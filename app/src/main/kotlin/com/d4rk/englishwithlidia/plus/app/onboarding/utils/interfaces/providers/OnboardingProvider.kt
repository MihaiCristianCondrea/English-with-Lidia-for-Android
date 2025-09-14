package com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.d4rk.android.libs.apptoolkit.app.onboarding.domain.data.model.ui.OnboardingPage
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.components.pages.CrashlyticsOnboardingPageTab
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.components.pages.FinalOnboardingPageTab
import com.d4rk.android.libs.apptoolkit.app.onboarding.utils.interfaces.providers.OnboardingProvider
import com.d4rk.englishwithlidia.plus.app.main.ui.MainActivity
import com.d4rk.englishwithlidia.plus.app.onboarding.utils.constants.OnboardingKeys

class AppOnboardingProvider : OnboardingProvider {

    override fun getOnboardingPages(context: Context): List<OnboardingPage> {
        return listOf(
            OnboardingPage.CustomPage(
                key = OnboardingKeys.CRASHLYTICS_OPTIONS,
                content = {
                    CrashlyticsOnboardingPageTab()
                }
            ),
            OnboardingPage.CustomPage(
                key = OnboardingKeys.ONBOARDING_COMPLETE,
                content = {
                    FinalOnboardingPageTab()
                }
            ),

            ).filter {
            when (it) {
                is OnboardingPage.DefaultPage -> it.isEnabled
                is OnboardingPage.CustomPage -> it.isEnabled
            }
        }
    }

    override fun onOnboardingFinished(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent)
            if (context is Activity) {
                context.finish()
            }
        } ?: Log.w("AppOnboardingProvider", "MainActivity not found to handle intent")
    }
}