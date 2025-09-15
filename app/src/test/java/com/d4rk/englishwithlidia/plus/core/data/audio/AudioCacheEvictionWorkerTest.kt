package com.d4rk.englishwithlidia.plus.core.data.audio

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import io.mockk.anyConstructed
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.assertEquals
import org.robolectric.RobolectricExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(RobolectricExtension::class)
class AudioCacheEvictionWorkerTest {

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `doWork returns success and evicts stale entries`() = runTest {
        mockkConstructor(AudioCacheManager::class)
        coEvery { anyConstructed<AudioCacheManager>().evictStaleEntries() } returns Unit

        val context: Context = ApplicationProvider.getApplicationContext()
        val worker = TestListenableWorkerBuilder<AudioCacheEvictionWorker>(context).build()

        val result = worker.doWork()

        assertEquals(ListenableWorker.Result.success(), result)
        coVerify(exactly = 1) { anyConstructed<AudioCacheManager>().evictStaleEntries() }
    }
}
