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

import com.d4rk.android.libs.apptoolkit.app.consent.data.local.ConsentPreferencesDataSource
import com.d4rk.android.libs.apptoolkit.app.consent.data.remote.datasource.ConsentRemoteDataSource
import com.d4rk.android.libs.apptoolkit.app.consent.data.remote.datasource.UmpConsentRemoteDataSource
import com.d4rk.android.libs.apptoolkit.app.consent.data.repository.ConsentRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.consent.domain.repository.ConsentRepository
import com.d4rk.android.libs.apptoolkit.app.consent.domain.usecases.ApplyConsentSettingsUseCase
import com.d4rk.android.libs.apptoolkit.app.consent.domain.usecases.ApplyInitialConsentUseCase
import com.d4rk.android.libs.apptoolkit.app.consent.domain.usecases.RequestConsentUseCase
import com.d4rk.android.libs.apptoolkit.core.data.local.datastore.CommonDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

val consentModule: Module = module {
    single<ConsentPreferencesDataSource> { get<CommonDataStore>() }
    single<ConsentRemoteDataSource> { UmpConsentRemoteDataSource() }
    single<ConsentRepository> {
        ConsentRepositoryImpl(
            remote = get(),
            local = get(),
            configProvider = get(),
            firebaseController = get(),
        )
    }
    single<RequestConsentUseCase> {
        RequestConsentUseCase(
            repository = get(),
            firebaseController = get()
        )
    }
    single<ApplyInitialConsentUseCase> {
        ApplyInitialConsentUseCase(
            repository = get(),
            firebaseController = get()
        )
    }
    single<ApplyConsentSettingsUseCase> {
        ApplyConsentSettingsUseCase(
            repository = get(),
            firebaseController = get()
        )
    }
}
