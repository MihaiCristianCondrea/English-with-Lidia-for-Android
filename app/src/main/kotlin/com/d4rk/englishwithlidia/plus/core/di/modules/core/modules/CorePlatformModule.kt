package com.d4rk.englishwithlidia.plus.core.di.modules.core.modules

import com.d4rk.android.libs.apptoolkit.core.data.firebase.FirebaseControllerImpl
import com.d4rk.android.libs.apptoolkit.core.domain.repository.FirebaseController
import com.d4rk.android.libs.apptoolkit.data.core.ads.AdsCoreManager
import com.d4rk.android.libs.apptoolkit.data.local.datastore.CommonDataStore
import com.d4rk.android.libs.apptoolkit.data.remote.client.KtorClient
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.core.data.local.datastore.DataStore
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    single {
        DataStore(
            context = get(),
            dispatchers = get()
        )
    }

    single<CommonDataStore> { get<DataStore>() }

    single<AdsCoreManager> {
        AdsCoreManager(
            context = get(),
            buildInfoProvider = get(),
            dispatchers = get()
        )
    }

    single<FirebaseController> { FirebaseControllerImpl() }

    single { KtorClient.createClient(enableLogging = BuildConfig.DEBUG) }
}
