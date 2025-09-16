package com.d4rk.englishwithlidia.plus.app.startup.utils.interfaces.providers

import android.content.Context
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.OnboardingActivity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppStartupProviderTest {

    @Test
    fun `getNextIntent returns intent targeting OnboardingActivity without unexpected extras`() {
        val context = mockk<Context>(relaxed = true) {
            every { packageName } returns "com.d4rk.englishwithlidia.plus"
        }

        val intent = AppStartupProvider().getNextIntent(context)

        val component = intent.component
        assertNotNull(component)
        assertEquals(OnboardingActivity::class.java.name, component?.className)
        assertTrue(
            intent.extras?.isEmpty ?: true,
            "AppStartupProvider should not attach extras when none are required"
        )
    }
}
