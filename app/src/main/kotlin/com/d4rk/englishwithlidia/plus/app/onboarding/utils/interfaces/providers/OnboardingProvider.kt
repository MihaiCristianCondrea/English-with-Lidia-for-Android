package com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Translate
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.model.OnboardingPage
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.views.pages.CrashlyticsOnboardingPageTab
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.views.pages.FinalOnboardingPageTab
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.views.pages.ThemeOnboardingPageTab
import com.d4rk.android.libs.apptoolkit.app.onboarding.utils.interfaces.providers.OnboardingProvider
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.app.main.ui.MainActivity
import com.d4rk.englishwithlidia.plus.app.onboarding.utils.constants.OnboardingKeys

class AppOnboardingProvider : OnboardingProvider {

    override fun getOnboardingPages(context: Context): List<OnboardingPage> {
        return listOf(
            OnboardingPage.DefaultPage(
                key = OnboardingKeys.WELCOME,
                title = context.getString(R.string.onboarding_welcome_title),
                description = context.getString(R.string.onboarding_welcome_description),
                imageVector = Icons.Outlined.Translate,
            ),
            OnboardingPage.DefaultPage(
                key = OnboardingKeys.LEARN,
                title = context.getString(R.string.onboarding_learn_title),
                description = context.getString(R.string.onboarding_learn_description),
                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
            ),
            OnboardingPage.CustomPage(
                key = OnboardingKeys.THEME_OPTIONS,
                content = {
                    ThemeOnboardingPageTab()
                },
            ),
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
        context.startActivity(Intent(context, MainActivity::class.java))
        if (context is Activity) {
            context.finish()
        }
    }
}