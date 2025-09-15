package com.d4rk.englishwithlidia.plus.core.data.audio

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.work.Configuration
import androidx.work.WorkManager
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.security.MessageDigest
import okio.Path.Companion.toPath

@OptIn(ExperimentalCoroutinesApi::class)
class AudioCacheManagerTest {

    private fun createContext(tempDir: File): Context = object : Application() {
        override fun getFilesDir(): File = tempDir
    }

    private fun initWorkManager(context: Context) {
        WorkManager.initialize(context, Configuration.Builder().build())
    }

    private fun createDataStore(tempDir: File, scope: kotlinx.coroutines.CoroutineScope): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            scope = scope,
            produceFile = { tempDir.resolve("datastore.preferences_pb").toPath() }
        )

    private fun setDataStore(manager: AudioCacheManager, dataStore: DataStore<Preferences>) {
        val field = AudioCacheManager::class.java.getDeclaredField("dataStore")
        field.isAccessible = true
        field.set(manager, dataStore)
    }

    private val json = Json { ignoreUnknownKeys = true }

    private fun hash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }

    @Test
    fun `resolve returns cached file uri and updates lastOpenedMs`() = runTest {
        val tempDir = createTempDir()
        val context = createContext(tempDir)
        initWorkManager(context)
        val dataStore = createDataStore(tempDir, this)

        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }

        val manager = AudioCacheManager(context, dispatchers)
        setDataStore(manager, dataStore)

        val contentId = "id"
        val remoteUrl = "https://example.com/audio.mp3"
        val cacheDir = File(tempDir, "audio_cache").apply { mkdirs() }
        val file = File(cacheDir, "cached.mp3").apply { writeBytes(byteArrayOf(1,2,3)) }
        val entry = AudioCacheManager.CacheEntry(
            url = remoteUrl,
            urlHash = hash(remoteUrl),
            filePath = file.absolutePath,
            lastOpenedMs = 1L,
            sizeBytes = file.length()
        )
        val key = stringPreferencesKey("audio.$contentId.blob")
        dataStore.edit { it[key] = json.encodeToString(entry) }

        val result = manager.resolve(contentId, remoteUrl)

        assertEquals(Uri.fromFile(file), result)
        val updated = json.decodeFromString<AudioCacheManager.CacheEntry>(dataStore.data.first()[key]!!)
        assertTrue(updated.lastOpenedMs > 1L)
    }

    @Test
    fun `resolve downloads when missing and stores file and datastore entry`() = runTest {
        val tempDir = createTempDir()
        val context = createContext(tempDir)
        initWorkManager(context)
        val dataStore = createDataStore(tempDir, this)
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val manager = AudioCacheManager(context, dispatchers)
        setDataStore(manager, dataStore)

        val server = MockWebServer().apply {
            enqueue(MockResponse().setBody(Buffer().write(byteArrayOf(4,5,6))))
            start()
        }
        val remoteUrl = server.url("/audio.mp3").toString()
        val contentId = "id"

        val result = manager.resolve(contentId, remoteUrl)

        val key = stringPreferencesKey("audio.$contentId.blob")
        val entry = json.decodeFromString<AudioCacheManager.CacheEntry>(dataStore.data.first()[key]!!)
        assertTrue(entry.filePath.isNotBlank())
        assertEquals(Uri.fromFile(File(entry.filePath)), result)
        assertTrue(File(entry.filePath).exists())
        assertEquals(3L, entry.sizeBytes)
        server.shutdown()
    }

    @Test
    fun `resolve returns original url on download failure`() = runTest {
        val tempDir = createTempDir()
        val context = createContext(tempDir)
        initWorkManager(context)
        val dataStore = createDataStore(tempDir, this)
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val manager = AudioCacheManager(context, dispatchers)
        setDataStore(manager, dataStore)

        val remoteUrl = "http://localhost:12345/missing.mp3"
        val contentId = "id"

        val result = manager.resolve(contentId, remoteUrl)

        assertEquals(remoteUrl, result.toString())
        val key = stringPreferencesKey("audio.$contentId.blob")
        val entry = json.decodeFromString<AudioCacheManager.CacheEntry>(dataStore.data.first()[key]!!)
        assertTrue(entry.filePath.isBlank())
    }
}
