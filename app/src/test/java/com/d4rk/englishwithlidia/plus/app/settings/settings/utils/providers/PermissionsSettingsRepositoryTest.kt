/*
package com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers

import android.content.Context
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsCategory
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsConfig
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsPreference
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PermissionsSettingsRepositoryTest {

    @Test
    fun `getPermissionsConfig emits expected sections`() = runBlocking(Dispatchers.Unconfined) {
        val stringMap = mapOf(
            R.string.permissions to "Permissions",
            R.string.normal to "Normal",
            R.string.access_network_state to "Access network state",
            R.string.summary_preference_permissions_access_network_state to "Access network state summary",
            R.string.ad_id to "Ad id",
            R.string.summary_preference_permissions_ad_id to "Ad id summary",
            R.string.billing to "Billing",
            R.string.summary_preference_permissions_billing to "Billing summary",
            R.string.check_license to "Check license",
            R.string.summary_preference_permissions_check_license to "Check license summary",
            R.string.foreground_service to "Foreground service",
            R.string.summary_preference_permissions_foreground_service to "Foreground service summary",
            R.string.internet to "Internet",
            R.string.summary_preference_permissions_internet to "Internet summary",
            R.string.wake_lock to "Wake lock",
            R.string.summary_preference_permissions_wake_lock to "Wake lock summary",
            R.string.runtime to "Runtime",
            R.string.post_notifications to "Post notifications",
            R.string.summary_preference_permissions_post_notifications to "Post notifications summary",
            R.string.special to "Special",
            R.string.access_notification_policy to "Access notification policy",
            R.string.summary_preference_permissions_access_notification_policy to "Access notification policy summary",
        )

        val context: Context = mockk {
            every { getString(any()) } answers { stringMap[firstArg<Int>()] ?: error("Unexpected string resource") }
        }
        val dispatchers: DispatcherProvider = mockk {
            every { io } returns Dispatchers.Unconfined
        }
        val repository = PermissionsSettingsRepository(context, dispatchers)

        val result = repository.getPermissionsConfig().first()

        val expected = SettingsConfig(
            title = stringMap.getValue(R.string.permissions),
            categories = listOf(
                SettingsCategory(
                    title = stringMap.getValue(R.string.normal),
                    preferences = listOf(
                        SettingsPreference(
                            title = stringMap.getValue(R.string.access_network_state),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_access_network_state),
                        ),
                        SettingsPreference(
                            title = stringMap.getValue(R.string.ad_id),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_ad_id),
                        ),
                        SettingsPreference(
                            title = stringMap.getValue(R.string.billing),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_billing),
                        ),
                        SettingsPreference(
                            title = stringMap.getValue(R.string.check_license),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_check_license),
                        ),
                        SettingsPreference(
                            title = stringMap.getValue(R.string.foreground_service),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_foreground_service),
                        ),
                        SettingsPreference(
                            title = stringMap.getValue(R.string.internet),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_internet),
                        ),
                        SettingsPreference(
                            title = stringMap.getValue(R.string.wake_lock),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_wake_lock),
                        ),
                    ),
                ),
                SettingsCategory(
                    title = stringMap.getValue(R.string.runtime),
                    preferences = listOf(
                        SettingsPreference(
                            title = stringMap.getValue(R.string.post_notifications),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_post_notifications),
                        ),
                    ),
                ),
                SettingsCategory(
                    title = stringMap.getValue(R.string.special),
                    preferences = listOf(
                        SettingsPreference(
                            title = stringMap.getValue(R.string.access_notification_policy),
                            summary = stringMap.getValue(R.string.summary_preference_permissions_access_notification_policy),
                        ),
                    ),
                ),
            ),
        )

        assertEquals(expected, result)
        verify(exactly = 1) { dispatchers.io }
    }
}
*/
