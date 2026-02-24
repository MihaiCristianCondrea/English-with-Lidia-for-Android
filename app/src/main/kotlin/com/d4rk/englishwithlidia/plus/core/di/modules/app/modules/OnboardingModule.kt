package com.d4rk.englishwithlidia.plus.core.di.modules.app.modules

import com.d4rk.android.libs.apptoolkit.app.onboarding.data.local.OnboardingPreferencesDataSource
import com.d4rk.android.libs.apptoolkit.app.onboarding.data.repository.OnboardingRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.onboarding.domain.repository.OnboardingRepository
import com.d4rk.android.libs.apptoolkit.app.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.d4rk.android.libs.apptoolkit.app.onboarding.domain.usecases.ObserveOnboardingCompletionUseCase
import com.d4rk.android.libs.apptoolkit.app.onboarding.ui.OnboardingViewModel
import com.d4rk.android.libs.apptoolkit.app.onboarding.utils.interfaces.providers.OnboardingProvider
import com.d4rk.android.libs.apptoolkit.core.data.local.datastore.CommonDataStore
import com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers.AppOnboardingProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingModule: Module = module {
    single<OnboardingProvider> { AppOnboardingProvider() }
    single<OnboardingPreferencesDataSource> { get<CommonDataStore>() }
    single<OnboardingRepository> { OnboardingRepositoryImpl(dataStore = get()) }
    single<ObserveOnboardingCompletionUseCase> { ObserveOnboardingCompletionUseCase(repository = get()) }
    single<CompleteOnboardingUseCase> { CompleteOnboardingUseCase(repository = get()) }

    viewModel {
        OnboardingViewModel(
            observeOnboardingCompletionUseCase = get(),
            completeOnboardingUseCase = get(),
            requestConsentUseCase = get(),
            dispatchers = get(),
            firebaseController = get(),
        )
    }
}
