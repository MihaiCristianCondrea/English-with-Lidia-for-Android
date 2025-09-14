package com.d4rk.englishwithlidia.plus.core.utils.extensions

import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun <T> ListenableFuture<T>.await(): T = suspendCancellableCoroutine { cont ->
    this.addListener({
        cont.resumeWith(runCatching { this.get() })
    }, MoreExecutors.directExecutor())
    cont.invokeOnCancellation { this.cancel(true) }
}
