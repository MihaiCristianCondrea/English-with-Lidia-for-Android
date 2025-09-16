package com.d4rk.englishwithlidia.plus.core.data.audio

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import io.mockk.any
import io.mockk.anyConstructed
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkConstructor
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import androidx.work.Operation
import androidx.work.WorkManager
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AudioCacheManagerTest {

    private lateinit var context: Context
    private lateinit var filesDir: File
    private lateinit var dispatchers: DispatcherProvider
    private lateinit var workManager: WorkManager
    private val json = Json { ignoreUnknownKeys = true }

    @BeforeEach
    fun setUp() {
        filesDir = Files.createTempDirectory("audio-cache-test").toFile()
        context = mockk(relaxed = true)
        every { context.applicationContext } returns context
        every { context.filesDir } returns filesDir
        every { context.noBackupFilesDir } returns filesDir
        every { context.packageName } returns "com.test.audio"

        dispatchers = mockk(relaxed = true)

        mockkStatic(WorkManager::class)
        workManager = mockk(relaxed = true)
        every { WorkManager.getInstance(any()) } returns workManager
        every { workManager.enqueueUniquePeriodicWork(any(), any(), any()) } returns mockk<Operation>(relaxed = true)
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(WorkManager::class)
        clearAllMocks()
        filesDir.deleteRecursively()
    }

    @Test
    fun `resolve returns cached uri when entry exists`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)
        val dataStore = manager.dataStore()
        val contentId = "cached"
        val remoteUrl = "https://example.com/audio.mp3"
        val urlHash = manager.hash(remoteUrl)
        val cacheDir = File(filesDir, "audio_cache").apply { mkdirs() }
        val cachedFile = File(cacheDir, "cached.mp3").apply { writeText("cached audio") }
        val originalEntry = AudioCacheManager.CacheEntry(
            url = remoteUrl,
            urlHash = urlHash,
            filePath = cachedFile.absolutePath,
            lastOpenedMs = 42L,
            sizeBytes = cachedFile.length()
        )
        dataStore.edit { prefs ->
            prefs[manager.entryKey(contentId)] = json.encodeToString(originalEntry)
        }

        mockkConstructor(URL::class)
        try {
            every { anyConstructed<URL>().openStream() } throws AssertionError("Should not download when cache is valid")

            val result = manager.resolve(contentId, remoteUrl)

            assertEquals(cachedFile.toURI().toString(), result.toString())
            val stored = dataStore.data.first()[manager.entryKey(contentId)]
            val decoded = json.decodeFromString<AudioCacheManager.CacheEntry>(stored!!)
            assertEquals(cachedFile.absolutePath, decoded.filePath)
            assertNotEquals(originalEntry.lastOpenedMs, decoded.lastOpenedMs)
            verify(exactly = 0) { anyConstructed<URL>().openStream() }
        } finally {
            unmockkConstructor(URL::class)
        }
    }

    @Test
    fun `resolve downloads new file when cached file missing`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)
        val dataStore = manager.dataStore()
        val contentId = "missing"
        val remoteUrl = "https://example.com/missing.mp3"
        val urlHash = manager.hash(remoteUrl)
        val missingFilePath = File(filesDir, "ghost.mp3").absolutePath
        val entry = AudioCacheManager.CacheEntry(
            url = remoteUrl,
            urlHash = urlHash,
            filePath = missingFilePath,
            lastOpenedMs = 0L,
            sizeBytes = 0L
        )
        dataStore.edit { prefs ->
            prefs[manager.entryKey(contentId)] = json.encodeToString(entry)
        }

        val downloaded = "downloaded audio".toByteArray()
        mockkConstructor(URL::class)
        try {
            every { anyConstructed<URL>().openStream() } returns downloaded.inputStream()

            val result = manager.resolve(contentId, remoteUrl)

            val target = File(File(filesDir, "audio_cache"), "${contentId}_${urlHash}.mp3")
            assertTrue(target.exists())
            assertArrayEquals(downloaded, target.readBytes())
            assertEquals(target.toURI().toString(), result.toString())

            val stored = dataStore.data.first()[manager.entryKey(contentId)]
            val decoded = json.decodeFromString<AudioCacheManager.CacheEntry>(stored!!)
            assertEquals(target.absolutePath, decoded.filePath)
            assertEquals(downloaded.size.toLong(), decoded.sizeBytes)
            verify(exactly = 1) { anyConstructed<URL>().openStream() }
        } finally {
            unmockkConstructor(URL::class)
        }
    }

    @Test
    fun `resolve cleans up temp file and falls back to remote url on download failure`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)
        val dataStore = manager.dataStore()
        val contentId = "failure"
        val remoteUrl = "https://example.com/failure.mp3"
        val urlHash = manager.hash(remoteUrl)
        val cacheDir = File(filesDir, "audio_cache").apply { mkdirs() }
        val tempFile = File(cacheDir, "${contentId}_${urlHash}.tmp").apply { writeText("partial") }
        val entry = AudioCacheManager.CacheEntry(
            url = remoteUrl,
            urlHash = urlHash,
            filePath = "",
            lastOpenedMs = 0L,
            sizeBytes = 0L
        )
        dataStore.edit { prefs ->
            prefs[manager.entryKey(contentId)] = json.encodeToString(entry)
        }

        mockkConstructor(URL::class)
        try {
            every { anyConstructed<URL>().openStream() } throws IOException("boom")

            val result = manager.resolve(contentId, remoteUrl)

            assertEquals(remoteUrl, result.toString())
            assertFalse(tempFile.exists())
            val stored = dataStore.data.first()[manager.entryKey(contentId)]
            val decoded = json.decodeFromString<AudioCacheManager.CacheEntry>(stored!!)
            assertEquals("", decoded.filePath)
            assertEquals(0L, decoded.sizeBytes)
            verify(exactly = 1) { anyConstructed<URL>().openStream() }

            val target = File(cacheDir, "${contentId}_${urlHash}.mp3")
            assertFalse(target.exists())
        } finally {
            unmockkConstructor(URL::class)
        }
    }

    @Test
    fun `resolve removes old file and downloads fresh media when url hash changes`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)
        val dataStore = manager.dataStore()
        val contentId = "mismatch"
        val oldUrl = "https://example.com/old.mp3"
        val newUrl = "https://example.com/new.mp3"
        val cacheDir = File(filesDir, "audio_cache").apply { mkdirs() }
        val oldFile = File(cacheDir, "old.mp3").apply { writeText("old audio") }
        val oldHash = manager.hash(oldUrl)
        val entry = AudioCacheManager.CacheEntry(
            url = oldUrl,
            urlHash = oldHash,
            filePath = oldFile.absolutePath,
            lastOpenedMs = 0L,
            sizeBytes = oldFile.length()
        )
        dataStore.edit { prefs ->
            prefs[manager.entryKey(contentId)] = json.encodeToString(entry)
        }

        val newHash = manager.hash(newUrl)
        val downloaded = "new audio".toByteArray()
        mockkConstructor(URL::class)
        try {
            every { anyConstructed<URL>().openStream() } returns downloaded.inputStream()

            val result = manager.resolve(contentId, newUrl)

            assertFalse(oldFile.exists())
            val target = File(cacheDir, "${contentId}_${newHash}.mp3")
            assertTrue(target.exists())
            assertEquals(target.toURI().toString(), result.toString())
            val stored = dataStore.data.first()[manager.entryKey(contentId)]
            val decoded = json.decodeFromString<AudioCacheManager.CacheEntry>(stored!!)
            assertEquals(newUrl, decoded.url)
            assertEquals(target.absolutePath, decoded.filePath)
            verify(exactly = 1) { anyConstructed<URL>().openStream() }
        } finally {
            unmockkConstructor(URL::class)
        }
    }

    @Test
    fun `evictStaleEntries removes expired files and leaves valid ones`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)
        val dataStore = manager.dataStore()
        val cacheDir = File(filesDir, "audio_cache").apply { mkdirs() }

        val staleContentId = "stale"
        val staleUrl = "https://example.com/stale.mp3"
        val staleFile = File(cacheDir, "stale.mp3").apply { writeText("stale audio") }
        val staleEntry = AudioCacheManager.CacheEntry(
            url = staleUrl,
            urlHash = manager.hash(staleUrl),
            filePath = staleFile.absolutePath,
            lastOpenedMs = System.currentTimeMillis() - THIRTY_DAYS_MS - 1_000,
            sizeBytes = staleFile.length()
        )

        val freshContentId = "fresh"
        val freshUrl = "https://example.com/fresh.mp3"
        val freshFile = File(cacheDir, "fresh.mp3").apply { writeText("fresh audio") }
        val freshEntry = AudioCacheManager.CacheEntry(
            url = freshUrl,
            urlHash = manager.hash(freshUrl),
            filePath = freshFile.absolutePath,
            lastOpenedMs = System.currentTimeMillis(),
            sizeBytes = freshFile.length()
        )

        val malformedKey = stringPreferencesKey("audio.bad.blob")

        dataStore.edit { prefs ->
            prefs[manager.entryKey(staleContentId)] = json.encodeToString(staleEntry)
            prefs[manager.entryKey(freshContentId)] = json.encodeToString(freshEntry)
            prefs[malformedKey] = "{malformed"
        }

        manager.evictStaleEntries()

        val prefs = dataStore.data.first()
        val staleStored = prefs[manager.entryKey(staleContentId)]?.let {
            json.decodeFromString<AudioCacheManager.CacheEntry>(it)
        }
        val freshStored = prefs[manager.entryKey(freshContentId)]?.let {
            json.decodeFromString<AudioCacheManager.CacheEntry>(it)
        }

        assertNotNull(staleStored)
        assertEquals("", staleStored!!.filePath)
        assertFalse(staleFile.exists())

        assertNotNull(freshStored)
        assertEquals(freshFile.absolutePath, freshStored!!.filePath)
        assertTrue(freshFile.exists())

        assertEquals("{malformed", prefs[malformedKey])
    }

    @Test
    fun `hash generates deterministic values`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)

        val first = manager.hash("input")
        val second = manager.hash("input")
        val third = manager.hash("other")

        assertEquals(first, second)
        assertNotEquals(first, third)
    }

    @Test
    fun `entryKey creates predictable preference names`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        every { dispatchers.io } returns testDispatcher
        val manager = AudioCacheManager(context, dispatchers, json)

        val first = manager.entryKey("abc")
        val second = manager.entryKey("abc")
        val third = manager.entryKey("xyz")

        assertEquals(first.name, second.name)
        assertEquals("audio.abc.blob", first.name)
        assertEquals("audio.xyz.blob", third.name)
    }

    private fun AudioCacheManager.dataStore(): DataStore<Preferences> {
        val field = AudioCacheManager::class.java.getDeclaredField("dataStore")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return field.get(this) as DataStore<Preferences>
    }

    private fun AudioCacheManager.entryKey(contentId: String) = AudioCacheManager::class.java
        .getDeclaredMethod("entryKey", String::class.java)
        .apply { isAccessible = true }
        .invoke(this, contentId) as Preferences.Key<String>

    private fun AudioCacheManager.hash(value: String) = AudioCacheManager::class.java
        .getDeclaredMethod("hash", String::class.java)
        .apply { isAccessible = true }
        .invoke(this, value) as String

    companion object {
        private const val THIRTY_DAYS_MS = 30L * 24 * 60 * 60 * 1000
    }
}
