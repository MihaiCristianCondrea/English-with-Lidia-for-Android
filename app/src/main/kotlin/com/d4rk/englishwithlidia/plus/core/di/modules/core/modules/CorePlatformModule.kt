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

package com.d4rk.englishwithlidia.plus.core.di.modules.core.modules

import com.d4rk.android.libs.apptoolkit.core.data.local.datastore.CommonDataStore
import com.d4rk.android.libs.apptoolkit.core.data.remote.ads.AdsCoreManager
import com.d4rk.android.libs.apptoolkit.core.data.remote.client.KtorClient
import com.d4rk.android.libs.apptoolkit.core.data.remote.firebase.FirebaseControllerImpl
import com.d4rk.android.libs.apptoolkit.core.domain.repository.FirebaseController
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.core.data.local.datastore.DataStore
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    single<DataStore> { DataStore(context = get(), dispatchers = get()) }
    single<CommonDataStore> { get<DataStore>() }
    single<AdsCoreManager> { AdsCoreManager(context = get(), buildInfoProvider = get(), dispatchers = get()) }
    single<FirebaseController> { FirebaseControllerImpl() }
    single { KtorClient.createClient(enableLogging = BuildConfig.DEBUG) }
}
