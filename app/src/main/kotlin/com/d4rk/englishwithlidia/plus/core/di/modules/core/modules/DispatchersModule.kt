package com.d4rk.englishwithlidia.plus.core.di.modules.core.modules

import com.d4rk.android.libs.apptoolkit.core.coroutines.dispatchers.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.coroutines.dispatchers.StandardDispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

val dispatchersModule: Module = module {
    single<DispatcherProvider> { StandardDispatchers() }
}

