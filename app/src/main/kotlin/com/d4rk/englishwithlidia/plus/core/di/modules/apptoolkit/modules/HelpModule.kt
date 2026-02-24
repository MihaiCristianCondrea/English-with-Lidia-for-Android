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

package com.d4rk.englishwithlidia.plus.core.di.modules.apptoolkit.modules

import com.d4rk.android.libs.apptoolkit.app.help.data.local.HelpLocalDataSource
import com.d4rk.android.libs.apptoolkit.app.help.data.remote.HelpRemoteDataSource
import com.d4rk.android.libs.apptoolkit.app.help.data.repository.FaqRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.help.domain.repository.FaqRepository
import com.d4rk.android.libs.apptoolkit.app.help.domain.usecases.GetFaqUseCase
import com.d4rk.android.libs.apptoolkit.app.help.ui.HelpViewModel
import com.d4rk.android.libs.apptoolkit.app.review.domain.usecases.ForceInAppReviewUseCase
import com.d4rk.android.libs.apptoolkit.core.coroutines.dispatchers.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.string.faqCatalogUrl
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.HelpConstants
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val helpModule: Module = module {
    single<HelpLocalDataSource> { HelpLocalDataSource(context = get()) }
    single<HelpRemoteDataSource> { HelpRemoteDataSource(client = get()) }
    single<FaqRepository> {
        FaqRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
            catalogUrl = com.d4rk.android.libs.apptoolkit.core.utils.constants.help.HelpConstants.FAQ_BASE_URL.faqCatalogUrl(
                isDebugBuild = BuildConfig.DEBUG
            ),
            productId = HelpConstants.FAQ_PRODUCT_ID,
            firebaseController = get(),
        )
    }
    single<GetFaqUseCase> { GetFaqUseCase(repository = get()) }

    viewModel {
        HelpViewModel(
            getFaqUseCase = get(),
            forceInAppReviewUseCase = get<ForceInAppReviewUseCase>(),
            dispatchers = get<DispatcherProvider>(),
            firebaseController = get(),
        )
    }
}
