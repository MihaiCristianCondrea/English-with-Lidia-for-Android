package com.d4rk.englishwithlidia.plus.core.data.audio

import android.content.Context
import android.test.mock.MockContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.WorkManager
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private const val THIRTY_DAYS_MS = 30L * 24 * 60 * 60 * 1000

private val Context.testDataStore: DataStore<Preferences> by preferencesDataStore(name = "audio_cache")

@OptIn(ExperimentalCoroutinesApi::class)
class AudioCacheManagerTest {

    private val json = Json { ignoreUnknownKeys = true }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `evictStaleEntries removes stale files and clears datastore`() = runTest {
        val tempDir = createTempDir()
        val context = TestContext(tempDir)

        mockkStatic(WorkManager::class)
        every { WorkManager.getInstance(any()) } returns mockk(relaxed = true)

        val dispatcherProvider: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }

        val now = System.currentTimeMillis()
        val cacheDir = File(tempDir, "audio_cache").apply { mkdirs() }
        val staleFile = File(cacheDir, "stale.mp3").apply { writeText("old") }
        val recentFile = File(cacheDir, "recent.mp3").apply { writeText("new") }

        val staleEntry = AudioCacheManager.CacheEntry(
            url = "staleUrl",
            urlHash = "staleHash",
            filePath = staleFile.absolutePath,
            lastOpenedMs = now - THIRTY_DAYS_MS - 1000,
            sizeBytes = staleFile.length()
        )
        val recentEntry = AudioCacheManager.CacheEntry(
            url = "recentUrl",
            urlHash = "recentHash",
            filePath = recentFile.absolutePath,
            lastOpenedMs = now,
            sizeBytes = recentFile.length()
        )

        val staleKey = stringPreferencesKey("audio.stale.blob")
        val recentKey = stringPreferencesKey("audio.recent.blob")

        context.testDataStore.edit { prefs ->
            prefs[staleKey] = json.encodeToString(staleEntry)
            prefs[recentKey] = json.encodeToString(recentEntry)
        }

        val manager = AudioCacheManager(context, dispatcherProvider, json)

        manager.evictStaleEntries()

        val prefs = context.testDataStore.data.first()
        val staleAfter = json.decodeFromString(AudioCacheManager.CacheEntry.serializer(), prefs[staleKey]!!)
        val recentAfter = json.decodeFromString(AudioCacheManager.CacheEntry.serializer(), prefs[recentKey]!!)

        assertFalse(staleFile.exists())
        assertEquals("", staleAfter.filePath)
        assertTrue(recentFile.exists())
        assertEquals(recentFile.absolutePath, recentAfter.filePath)
    }

    private class TestContext(private val dir: File) : MockContext() {
        override fun getFilesDir(): File = dir
        override fun getApplicationContext(): Context = this
        override fun getPackageName(): String = "com.d4rk.englishwithlidia.plus"
    }
}

