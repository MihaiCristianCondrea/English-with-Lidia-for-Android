/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

/*
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
*/
