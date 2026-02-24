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

package com.d4rk.englishwithlidia.plus.core.di

import android.content.Context
import com.d4rk.englishwithlidia.plus.core.di.modules.app.modules.adsModule
import com.d4rk.englishwithlidia.plus.core.di.modules.app.modules.appModule
import com.d4rk.englishwithlidia.plus.core.di.modules.app.modules.consentModule
import com.d4rk.englishwithlidia.plus.core.di.modules.app.modules.lessonsModule
import com.d4rk.englishwithlidia.plus.core.di.modules.app.modules.onboardingModule
import com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.appToolkitModules
import com.d4rk.englishwithlidia.plus.core.di.modules.core.modules.coreModule
import com.d4rk.englishwithlidia.plus.core.di.modules.core.modules.dispatchersModule
import com.d4rk.englishwithlidia.plus.core.di.modules.settings.modules.themeModule
import com.d4rk.englishwithlidia.plus.core.di.modules.settings.settingsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun initializeKoin(context: Context) {
    startKoin {
        androidContext(androidContext = context)
        modules(
            modules = buildList {
                add(dispatchersModule)
                add(coreModule)
                add(appModule)
                add(lessonsModule)
                add(consentModule)
                addAll(settingsModules)
                add(adsModule)
                addAll(appToolkitModules)
                add(themeModule)
                add(onboardingModule)
            }
        )
    }
}
