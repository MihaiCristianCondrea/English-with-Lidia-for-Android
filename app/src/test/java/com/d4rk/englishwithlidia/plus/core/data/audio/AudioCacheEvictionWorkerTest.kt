package com.d4rk.englishwithlidia.plus.core.data.audio

import android.content.Context
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerParameters
import io.mockk.anyConstructed
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AudioCacheEvictionWorkerTest {

    private val context: Context = mockk(relaxed = true).apply {
        every { applicationContext } returns this@apply
    }
    private val workerParameters: WorkerParameters = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        mockkConstructor(AudioCacheManager::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkConstructor(AudioCacheManager::class)
    }

    @Test
    fun `doWork returns success when eviction succeeds`() = runTest {
        coEvery { anyConstructed<AudioCacheManager>().evictStaleEntries() } returns Unit

        val worker = AudioCacheEvictionWorker(context, workerParameters)

        val result = worker.doWork()

        assertEquals(Result.success(), result)
    }

    @Test
    fun `doWork returns failure when eviction throws`() = runTest {
        coEvery { anyConstructed<AudioCacheManager>().evictStaleEntries() } throws IllegalStateException()

        val worker = AudioCacheEvictionWorker(context, workerParameters)

        val result = worker.doWork()

        assertEquals(Result.failure(), result)
    }
}
