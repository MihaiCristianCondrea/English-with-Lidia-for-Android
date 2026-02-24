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
import com.d4rk.android.libs.apptoolkit.app.ads.ui.AdsSettingsActivity
import com.d4rk.android.libs.apptoolkit.app.permissions.ui.PermissionsActivity
import com.d4rk.android.libs.apptoolkit.app.settings.general.ui.GeneralSettingsActivity
import com.d4rk.android.libs.apptoolkit.app.settings.utils.constants.SettingsContent
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.PrivacySettingsProvider
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.context.openActivity

class AppPrivacySettingsProvider(val context: Context) : PrivacySettingsProvider {

    override fun openPermissionsScreen() {
        context.openActivity(PermissionsActivity::class.java)
    }

    override fun openAdsScreen() {
        context.openActivity(AdsSettingsActivity::class.java)
    }

    override fun openUsageAndDiagnosticsScreen() {
        GeneralSettingsActivity.start(
            context = context,
            title = context.getString(R.string.usage_and_diagnostics),
            contentKey = SettingsContent.USAGE_AND_DIAGNOSTICS
        )
    }
}