package com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.d4rk.android.libs.apptoolkit.app.onboarding.domain.data.model.ui.OnboardingPage
import com.d4rk.englishwithlidia.plus.app.main.ui.MainActivity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.anyConstructed
import io.mockk.any
import io.mockk.constructedWith
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify

@ExtendWith(MockKExtension::class)
class AppOnboardingProviderTest {

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getOnboardingPages filters out disabled pages`() {
        mockkConstructor(OnboardingPage.CustomPage::class)

        val context = mockk<Context>(relaxed = true)
        val isEnabledCalls = mutableListOf<Boolean>()

        every { anyConstructed<OnboardingPage.CustomPage>().isEnabled } answers {
            val nextValue = isEnabledCalls.isEmpty()
            isEnabledCalls.add(nextValue)
            nextValue
        }

        val provider = AppOnboardingProvider()

        val pages = provider.getOnboardingPages(context)

        assertEquals(listOf(true, false), isEnabledCalls)
        assertEquals(1, pages.size)
    }

    @Test
    fun `onOnboardingFinished launches MainActivity and finishes current activity`() {
        mockkConstructor(Intent::class)

        val packageManager = mockk<PackageManager>()
        val componentName = ComponentName("com.d4rk.englishwithlidia.plus", MainActivity::class.java.name)
        val activity = mockk<Activity>(relaxed = true)

        every { activity.packageManager } returns packageManager
        every { anyConstructed<Intent>().resolveActivity(packageManager) } returns componentName

        val provider = AppOnboardingProvider()

        provider.onOnboardingFinished(activity)

        verify { constructedWith<Intent>(activity, MainActivity::class.java) }
        verify(exactly = 1) { activity.startActivity(anyConstructed<Intent>()) }
        verify(exactly = 1) { activity.finish() }
    }

    @Test
    fun `onOnboardingFinished logs warning when MainActivity cannot handle intent`() {
        mockkConstructor(Intent::class)
        mockkStatic(Log::class)

        val packageManager = mockk<PackageManager>()
        val activity = mockk<Activity>(relaxed = true)

        every { activity.packageManager } returns packageManager
        every { anyConstructed<Intent>().resolveActivity(packageManager) } returns null
        every { Log.w("AppOnboardingProvider", "MainActivity not found to handle intent") } returns 0

        val provider = AppOnboardingProvider()

        provider.onOnboardingFinished(activity)

        verify { constructedWith<Intent>(activity, MainActivity::class.java) }
        verify { Log.w("AppOnboardingProvider", "MainActivity not found to handle intent") }
        verify(exactly = 0) { activity.startActivity(any()) }
        verify(exactly = 0) { activity.finish() }
    }
}
