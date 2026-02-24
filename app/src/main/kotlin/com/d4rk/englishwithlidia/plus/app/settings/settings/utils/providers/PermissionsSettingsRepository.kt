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
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.app.permissions.domain.repository.PermissionsRepository
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsCategory
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsConfig
import com.d4rk.android.libs.apptoolkit.app.settings.settings.domain.model.SettingsPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PermissionsSettingsRepository(
    private val context: Context,
) : PermissionsRepository {

    override fun getPermissionsConfig(): Flow<SettingsConfig> =
        flow {
            emit(
                SettingsConfig(
                    title = context.getString(R.string.permissions),
                    categories = listOf(
                        SettingsCategory(
                            title = context.getString(R.string.normal),
                            preferences = listOf(
                                SettingsPreference(
                                    title = context.getString(R.string.access_network_state),
                                    summary = context.getString(R.string.summary_preference_permissions_access_network_state),
                                ),
                                SettingsPreference(
                                    title = context.getString(R.string.ad_id),
                                    summary = context.getString(R.string.summary_preference_permissions_ad_id),
                                ),
                                SettingsPreference(
                                    title = context.getString(R.string.billing),
                                    summary = context.getString(R.string.summary_preference_permissions_billing),
                                ),
                                SettingsPreference(
                                    title = context.getString(R.string.check_license),
                                    summary = context.getString(R.string.summary_preference_permissions_check_license),
                                ),
                                SettingsPreference(
                                    title = context.getString(R.string.foreground_service),
                                    summary = context.getString(R.string.summary_preference_permissions_foreground_service),
                                ),
                                SettingsPreference(
                                    title = context.getString(R.string.internet),
                                    summary = context.getString(R.string.summary_preference_permissions_internet),
                                ),
                                SettingsPreference(
                                    title = context.getString(R.string.wake_lock),
                                    summary = context.getString(R.string.summary_preference_permissions_wake_lock),
                                ),
                            ),
                        ),
                        SettingsCategory(
                            title = context.getString(R.string.runtime),
                            preferences = listOf(
                                SettingsPreference(
                                    title = context.getString(R.string.post_notifications),
                                    summary = context.getString(R.string.summary_preference_permissions_post_notifications),
                                ),
                            ),
                        ),
                        SettingsCategory(
                            title = context.getString(R.string.special),
                            preferences = listOf(
                                SettingsPreference(
                                    title = context.getString(R.string.access_notification_policy),
                                    summary = context.getString(R.string.summary_preference_permissions_access_notification_policy),
                                ),
                            ),
                        ),
                    ),
                ),
            )
        }
}