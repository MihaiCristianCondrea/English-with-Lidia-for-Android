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

import com.d4rk.android.libs.apptoolkit.app.diagnostics.data.repository.UsageAndDiagnosticsRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.diagnostics.domain.repository.UsageAndDiagnosticsRepository
import com.d4rk.android.libs.apptoolkit.app.diagnostics.ui.UsageAndDiagnosticsViewModel
import com.d4rk.android.libs.apptoolkit.core.data.local.datastore.CommonDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val usageAndDiagnosticsModule: Module = module {
    single<UsageAndDiagnosticsRepository> {
        UsageAndDiagnosticsRepositoryImpl(
            dataSource = get<CommonDataStore>(),
            configProvider = get(),
            dispatchers = get(),
            firebaseController = get(),
        )
    }

    viewModel {
        UsageAndDiagnosticsViewModel(
            repository = get(),
            firebaseController = get(),
            dispatchers = get(),
            applyConsentSettingsUseCase = get(),
        )
    }
}
