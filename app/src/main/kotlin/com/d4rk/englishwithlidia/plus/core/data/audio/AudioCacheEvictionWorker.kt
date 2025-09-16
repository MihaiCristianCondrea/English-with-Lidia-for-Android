package com.d4rk.englishwithlidia.plus.core.data.audio

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.d4rk.android.libs.apptoolkit.core.di.StandardDispatchers
import kotlinx.coroutines.CancellationException

class AudioCacheEvictionWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val manager = AudioCacheManager(appContext, StandardDispatchers())

    override suspend fun doWork(): Result = try {
        manager.evictStaleEntries()
        Result.success()
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (exception: Exception) {
        Result.failure()
    }
}
