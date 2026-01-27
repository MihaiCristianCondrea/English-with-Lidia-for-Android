package com.d4rk.englishwithlidia.plus.player.audio

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

private val Context.audioCacheStore: DataStore<Preferences> by preferencesDataStore(name = "audio_cache")

private const val CACHE_DIR = "audio_cache"
private const val EVICTION_WORK = "audio_cache_eviction"
private const val THIRTY_DAYS_MS = 30L * 24 * 60 * 60 * 1000

class AudioCacheManager(
    private val context: Context,
    private val dispatchers: DispatcherProvider,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    private val dataStore = context.audioCacheStore

    init {
        scheduleEviction()
    }

    @Serializable
    data class CacheEntry(
        val url: String,
        val urlHash: String,
        val filePath: String,
        val lastOpenedMs: Long,
        val sizeBytes: Long
    )

    private fun hash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }

    private fun entryKey(contentId: String) = stringPreferencesKey("audio.$contentId.blob")

    suspend fun resolve(contentId: String, remoteUrl: String): Uri = withContext(dispatchers.io) {
        val now = System.currentTimeMillis()
        val urlHash = hash(remoteUrl)
        val key = entryKey(contentId)
        val entry = dataStore.data.first()[key]?.let { json.decodeFromString<CacheEntry>(it) }

        if (entry != null && entry.urlHash == urlHash && entry.filePath.isNotBlank()) {
            val file = File(entry.filePath)
            if (file.exists()) {
                dataStore.edit { it[key] = json.encodeToString(entry.copy(lastOpenedMs = now)) }
                return@withContext file.toURI().toString().let(Uri::parse)
            }
        }

        entry?.filePath?.takeIf { it.isNotBlank() && it != remoteUrl }?.let { File(it).delete() }

        val cacheDir = File(context.filesDir, CACHE_DIR).apply { if (!exists()) mkdirs() }
        val target = File(cacheDir, "${contentId}_${urlHash}.mp3")
        val temp = File(cacheDir, "${contentId}_${urlHash}.tmp")

        val resultUri = runCatching {
            URL(remoteUrl).openStream().use { input ->
                FileOutputStream(temp).use { output ->
                    input.copyTo(output)
                }
            }
            if (!temp.renameTo(target)) {
                throw IOException("Failed to rename temp file to target")
            }
            val size = target.length()
            val newEntry = CacheEntry(remoteUrl, urlHash, target.absolutePath, now, size)
            dataStore.edit { it[key] = json.encodeToString(newEntry) }
            target.toURI().toString().let(Uri::parse)
        }.getOrElse {
            temp.delete()
            val newEntry = CacheEntry(remoteUrl, urlHash, "", now, 0)
            dataStore.edit { it[key] = json.encodeToString(newEntry) }
            remoteUrl.toUri()
        }
        resultUri
    }

    suspend fun evictStaleEntries() = withContext(dispatchers.io) {
        val now = System.currentTimeMillis()
        val evictionKey = longPreferencesKey("audio.cache.eviction_last_run_ms")
        dataStore.edit { prefs ->
            prefs[evictionKey] = now
            val keys =
                prefs.asMap().keys.filter { it.name.startsWith("audio.") && it.name.endsWith(".blob") }
            keys.forEach { prefKey ->
                val key = stringPreferencesKey(prefKey.name)
                val value = prefs[key] ?: return@forEach
                val entry = runCatching { json.decodeFromString<CacheEntry>(value) }.getOrNull()
                    ?: return@forEach
                if (now - entry.lastOpenedMs > THIRTY_DAYS_MS) {
                    entry.filePath.takeIf { it.isNotBlank() }?.let { File(it).delete() }
                    val updated = entry.copy(filePath = "")
                    prefs[key] = json.encodeToString(updated)
                }
            }
        }
    }

    private fun scheduleEviction() {
        val work = PeriodicWorkRequestBuilder<AudioCacheEvictionWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            EVICTION_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
    }
}
