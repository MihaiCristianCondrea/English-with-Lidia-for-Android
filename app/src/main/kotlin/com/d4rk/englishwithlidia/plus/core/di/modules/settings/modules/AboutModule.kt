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

package com.d4rk.englishwithlidia.plus.core.di.modules.settings.modules

import com.d4rk.android.libs.apptoolkit.app.about.data.repository.AboutRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.about.domain.repository.AboutRepository
import com.d4rk.android.libs.apptoolkit.app.about.domain.usecases.CopyDeviceInfoUseCase
import com.d4rk.android.libs.apptoolkit.app.about.domain.usecases.GetAboutInfoUseCase
import com.d4rk.android.libs.apptoolkit.app.about.ui.AboutViewModel
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.AboutSettingsProvider
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.BuildInfoProvider
import com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers.AppAboutSettingsProvider
import com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers.AppBuildInfoProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val aboutModule: Module = module {
    single<AboutSettingsProvider> { AppAboutSettingsProvider(context = get()) }
    single<BuildInfoProvider> { AppBuildInfoProvider() }
    single<AboutRepository> { AboutRepositoryImpl(deviceProvider = get(), buildInfoProvider = get(), context = get(), firebaseController = get()) }
    single<GetAboutInfoUseCase> { GetAboutInfoUseCase(repository = get(), firebaseController = get()) }
    single<CopyDeviceInfoUseCase> { CopyDeviceInfoUseCase(repository = get(), firebaseController = get()) }

    viewModel {
        AboutViewModel(
            getAboutInfo = get(),
            copyDeviceInfo = get(),
            dispatchers = get(),
            firebaseController = get(),
        )
    }
}
