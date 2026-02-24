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

package com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.app.settings.general.ui.GeneralSettingsActivity
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsCategory
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsConfig
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsPreference
import com.d4rk.android.libs.apptoolkit.app.settings.utils.constants.SettingsContent
import com.d4rk.android.libs.apptoolkit.app.settings.utils.interfaces.SettingsProvider
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.context.openAppNotificationSettings
import com.d4rk.englishwithlidia.plus.app.settings.settings.utils.constants.SettingsConstants

class AppSettingsProvider : SettingsProvider {
    override fun provideSettingsConfig(context: Context): SettingsConfig {
        return SettingsConfig(
            title = context.getString(R.string.settings),
            categories = listOf(
                SettingsCategory(
                    preferences = listOf(
                        SettingsPreference(
                            key = SettingsConstants.KEY_SETTINGS_NOTIFICATION,
                            icon = Icons.Outlined.Notifications,
                            title = context.getString(R.string.notifications),
                            summary = context.getString(R.string.summary_preference_settings_notifications),
                            action = {
                                val opened = context.openAppNotificationSettings()
                                if (!opened) {
                                    GeneralSettingsActivity.start(
                                        context = context,
                                        title = context.getString(R.string.security_and_privacy),
                                        contentKey = SettingsContent.SECURITY_AND_PRIVACY,
                                    )
                                }
                            },
                        ),
                        SettingsPreference(
                            key = SettingsContent.DISPLAY,
                            icon = Icons.Outlined.Palette,
                            title = context.getString(R.string.display),
                            summary = context.getString(R.string.summary_preference_settings_display),
                            action = {
                                GeneralSettingsActivity.start(
                                    context = context,
                                    title = context.getString(R.string.display),
                                    contentKey = SettingsContent.DISPLAY,
                                )
                            },
                        ),
                    ),
                ),
                SettingsCategory(
                    preferences = listOf(
                        SettingsPreference(
                            key = SettingsContent.SECURITY_AND_PRIVACY,
                            icon = Icons.Outlined.Security,
                            title = context.getString(R.string.security_and_privacy),
                            summary = context.getString(R.string.summary_preference_settings_privacy_and_security),
                            action = {
                                GeneralSettingsActivity.start(
                                    context = context,
                                    title = context.getString(R.string.security_and_privacy),
                                    contentKey = SettingsContent.SECURITY_AND_PRIVACY,
                                )
                            },
                        ),
                        SettingsPreference(
                            key = SettingsContent.ADVANCED,
                            icon = Icons.Outlined.Build,
                            title = context.getString(R.string.advanced),
                            summary = context.getString(R.string.summary_preference_settings_advanced),
                            action = {
                                GeneralSettingsActivity.start(
                                    context = context,
                                    title = context.getString(R.string.advanced),
                                    contentKey = SettingsContent.ADVANCED,
                                )
                            },
                        ),
                        SettingsPreference(
                            key = SettingsContent.ABOUT,
                            icon = Icons.Outlined.Info,
                            title = context.getString(R.string.about),
                            summary = context.getString(R.string.summary_preference_settings_about),
                            action = {
                                GeneralSettingsActivity.start(
                                    context = context,
                                    title = context.getString(R.string.about),
                                    contentKey = SettingsContent.ABOUT,
                                )
                            },
                        ),
                    ),
                ),
            ),
        )
    }
}
