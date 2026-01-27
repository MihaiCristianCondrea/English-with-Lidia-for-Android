package com.d4rk.englishwithlidia.plus.core.di.modules.settings.modules

import com.d4rk.android.libs.apptoolkit.app.permissions.data.repository.PermissionsRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.permissions.domain.repository.PermissionsRepository
import com.d4rk.android.libs.apptoolkit.app.permissions.ui.PermissionsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val permissionsModule: Module =
    module {
        single<PermissionsRepository> {
            PermissionsRepositoryImpl(
                context = get(),
                dispatchers = get()
            )
        }
        viewModel {
            PermissionsViewModel(
                permissionsRepository = get(),
                firebaseController = get(),
            )
        }
    }
