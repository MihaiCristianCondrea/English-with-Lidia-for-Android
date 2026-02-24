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

package com.d4rk.englishwithlidia.plus.core.di.modules.app.modules

import com.d4rk.android.libs.apptoolkit.app.main.data.repository.InAppUpdateRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.main.domain.repository.InAppUpdateRepository
import com.d4rk.android.libs.apptoolkit.app.main.domain.repository.NavigationRepository
import com.d4rk.android.libs.apptoolkit.app.main.domain.usecases.RequestInAppUpdateUseCase
import com.d4rk.android.libs.apptoolkit.app.main.ui.factory.GmsHostFactory
import com.d4rk.android.libs.apptoolkit.app.review.domain.usecases.RequestInAppReviewUseCase
import com.d4rk.android.libs.apptoolkit.core.utils.constants.api.ApiLanguages
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.boolean.toApiEnvironment
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.string.developerAppsApiUrl
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.main.data.repository.MainNavigationRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.main.domain.usecases.GetNavigationDrawerItemsUseCase
import com.d4rk.englishwithlidia.plus.app.main.ui.MainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule: Module = module {
    single { GmsHostFactory() }
    single<NavigationRepository> { MainNavigationRepositoryImpl(firebaseController = get()) }
    single<GetNavigationDrawerItemsUseCase> {
        GetNavigationDrawerItemsUseCase(navigationRepository = get(), firebaseController = get())
    }
    single<InAppUpdateRepository> { InAppUpdateRepositoryImpl() }
    single { RequestInAppUpdateUseCase(repository = get()) }
    viewModel {
        MainViewModel(
            getNavigationDrawerItemsUseCase = get(),
            requestConsentUseCase = get(),
            requestInAppReviewUseCase = get<RequestInAppReviewUseCase>(),
            requestInAppUpdateUseCase = get<RequestInAppUpdateUseCase>(),
            firebaseController = get(),
            dispatchers = get(),
        )
    }

    single<String>(qualifier = named(name = "developer_apps_api_url")) {
        val environment = BuildConfig.DEBUG.toApiEnvironment()
        environment.developerAppsApiUrl(language = ApiLanguages.DEFAULT)
    }
}
