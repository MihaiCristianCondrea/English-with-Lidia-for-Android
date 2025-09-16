package com.d4rk.englishwithlidia.plus.core.utils.extensions

import com.google.common.util.concurrent.SettableFuture
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutionException
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
    fun `await throws CancellationException when future is cancelled`() = runTest {
        val future = SettableFuture.create<Int>()
        future.cancel(true)

        val thrown = try {
            future.await()
            null
        } catch (error: Throwable) {
            error
        }

        assertTrue(future.isCancelled)
        assertTrue(thrown is CancellationException)
    }

    @Test
    fun `await propagates listener errors`() = runTest {
        val future = SettableFuture.create<Int>()
        val failure = IllegalStateException("boom")
        future.setException(failure)

        val thrown = try {
            future.await()
            null
        } catch (error: Throwable) {
            error
        }

        assertTrue(thrown is ExecutionException)
        assertEquals(failure, (thrown as ExecutionException).cause)
    }
}
