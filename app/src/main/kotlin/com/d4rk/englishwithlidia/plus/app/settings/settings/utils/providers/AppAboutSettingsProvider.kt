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
import android.os.Build
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.AboutSettingsProvider
import com.d4rk.englishwithlidia.plus.BuildConfig

class AppAboutSettingsProvider(val context: Context) : AboutSettingsProvider {
    override val deviceInfo: String
        get() {
            return context.getString(
                R.string.app_build,
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.manufacturer)} ${Build.MANUFACTURER}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.device_model)} ${Build.MODEL}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.android_version)} ${Build.VERSION.RELEASE}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.api_level)} ${Build.VERSION.SDK_INT}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.arch)} ${Build.SUPPORTED_ABIS.joinToString()}",
                if (BuildConfig.DEBUG) context.getString(com.d4rk.android.libs.apptoolkit.R.string.debug) else context.getString(
                    com.d4rk.android.libs.apptoolkit.R.string.release
                )
            )
        }
}