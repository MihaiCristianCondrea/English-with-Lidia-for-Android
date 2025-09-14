package com.d4rk.englishwithlidia.plus.core.di.modules

import com.d4rk.android.libs.apptoolkit.app.main.data.repository.MainRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.main.domain.repository.NavigationRepository
import com.d4rk.android.libs.apptoolkit.app.onboarding.utils.interfaces.providers.OnboardingProvider
import com.d4rk.android.libs.apptoolkit.data.client.KtorClient
import com.d4rk.android.libs.apptoolkit.data.core.ads.AdsCoreManager
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.LessonMapper
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.LessonRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.LessonViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.HomeRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.HomeViewModel
import com.d4rk.englishwithlidia.plus.app.main.ui.MainViewModel
import com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers.AppOnboardingProvider
import com.d4rk.englishwithlidia.plus.core.data.datastore.DataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule : Module = module {
    single<DataStore> { DataStore(context = get(), dispatchers = get()) }
    single<AdsCoreManager> { AdsCoreManager(context = get(), buildInfoProvider = get(), dispatchers = get()) }
    single { KtorClient.createClient(enableLogging = BuildConfig.DEBUG) }

    single<OnboardingProvider> { AppOnboardingProvider() }

    single<NavigationRepository> { MainRepositoryImpl(dispatchers = get()) }

    viewModel { MainViewModel(navigationRepository = get()) }

    //single<String>(qualifier = named(name = "developer_apps_base_url")) { BuildConfig.DEVELOPER_APPS_BASE_URL } // TODO: Make the API link in gradle

    // Lessons
    single<HomeRepository> { HomeRepositoryImpl(client = get()) }
    factory { GetHomeLessonsUseCase(repository = get()) }
    viewModel { HomeViewModel(getHomeLessonsUseCase = get(), dispatcherProvider = get()) }

    single { LessonMapper() }
    single<LessonRepository> { LessonRepositoryImpl(client = get(), dispatchers = get(), mapper = get()) }
    factory { GetLessonUseCase(repository = get()) }
    viewModel { LessonViewModel(getLessonUseCase = get()) }
}