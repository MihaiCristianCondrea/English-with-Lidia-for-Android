package com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import io.mockk.any
import io.mockk.anyConstructed
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkConstructor
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AppOnboardingProviderTest {

    @BeforeEach
    fun setUp() {
        mockkStatic(Log::class)
        mockkConstructor(Intent::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkConstructor(Intent::class)
        unmockkStatic(Log::class)
    }

    @Test
    fun `onOnboardingFinished logs warning when MainActivity is missing`() {
        val context = mockk<Activity>(relaxed = true)
        val packageManager = mockk<PackageManager>()
        every { context.packageManager } returns packageManager

        every { anyConstructed<Intent>().resolveActivity(packageManager) } returns null
        every { Log.w("AppOnboardingProvider", "MainActivity not found to handle intent") } returns 0

        val provider = AppOnboardingProvider()

        provider.onOnboardingFinished(context)

        verify(exactly = 1) {
            Log.w("AppOnboardingProvider", "MainActivity not found to handle intent")
        }
        verify(exactly = 0) { context.startActivity(any()) }
        verify(exactly = 0) { context.finish() }
    }
}
