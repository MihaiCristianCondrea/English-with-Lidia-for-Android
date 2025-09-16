package com.d4rk.englishwithlidia.plus

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class EnglishWithLidiaTest {

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `onTerminate removes ProcessLifecycleOwner observer`() {
        mockkStatic(ProcessLifecycleOwner::class)

        val processLifecycle = mockk<Lifecycle>(relaxUnitFun = true)
        val lifecycleOwner = mockk<LifecycleOwner> {
            every { lifecycle } returns processLifecycle
        }

        every { ProcessLifecycleOwner.get() } returns lifecycleOwner

        val application = EnglishWithLidia()

        runCatching { application.onTerminate() }

        verify(exactly = 1) {
            processLifecycle.removeObserver(application)
        }
    }
}
