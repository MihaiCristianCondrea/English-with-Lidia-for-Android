package com.d4rk.englishwithlidia.plus.core.di.modules.settings.modules

import com.d4rk.android.libs.apptoolkit.app.settings.general.data.repository.GeneralSettingsRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.settings.general.domain.repository.GeneralSettingsRepository
import com.d4rk.android.libs.apptoolkit.app.settings.general.ui.GeneralSettingsViewModel
import com.d4rk.android.libs.apptoolkit.app.settings.general.ui.content.GeneralSettingsContentProvider
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.DisplaySettingsProvider
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.PrivacySettingsProvider
import com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers.AppDisplaySettingsProvider
import com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers.AppPrivacySettingsProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val generalSettingsModule: Module = module {
    single<DisplaySettingsProvider> { AppDisplaySettingsProvider(context = get()) }
    single<GeneralSettingsContentProvider> { GeneralSettingsContentProvider() }
    single<PrivacySettingsProvider> { AppPrivacySettingsProvider(context = get()) }
    single<GeneralSettingsRepository> { GeneralSettingsRepositoryImpl() }

    viewModel {
        GeneralSettingsViewModel(
            repository = get(),
            dispatchers = get(),
            firebaseController = get(),
        )
    }
}
