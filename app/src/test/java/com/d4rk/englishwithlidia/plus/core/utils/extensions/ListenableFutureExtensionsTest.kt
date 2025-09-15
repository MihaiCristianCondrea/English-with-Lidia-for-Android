package com.d4rk.englishwithlidia.plus.core.utils.extensions

import com.google.common.util.concurrent.SettableFuture
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ListenableFutureExtensionsTest {

    @Test
    fun `await returns value when future completes`() = runTest {
        val future = SettableFuture.create<Int>()
        future.set(42)

        val result = future.await()

        assertEquals(42, result)
    }

    @Test
    fun `await cancels future when coroutine is cancelled`() = runTest {
        val future = SettableFuture.create<Int>()
        val job = launch(start = CoroutineStart.UNDISPATCHED) { future.await() }

        job.cancelAndJoin()

        assertTrue(future.isCancelled)
    }
}
