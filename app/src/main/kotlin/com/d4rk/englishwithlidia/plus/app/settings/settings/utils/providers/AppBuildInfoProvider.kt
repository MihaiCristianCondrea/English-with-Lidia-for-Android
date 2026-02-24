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
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.BuildInfoProvider
import com.d4rk.englishwithlidia.plus.BuildConfig

class AppBuildInfoProvider : BuildInfoProvider {

    override val packageName: String get() = BuildConfig.APPLICATION_ID

    override val appVersion: String get() = BuildConfig.VERSION_NAME

    override val appVersionCode: Int
        get() {
            return BuildConfig.VERSION_CODE
        }

    override val isDebugBuild: Boolean
        get() {
            return BuildConfig.DEBUG
        }
}
