package com.d4rk.englishwithlidia.plus.core.di.modules

import com.d4rk.android.libs.apptoolkit.app.main.data.repository.MainRepositoryImpl
import com.d4rk.android.libs.apptoolkit.app.main.domain.repository.NavigationRepository
import com.d4rk.android.libs.apptoolkit.app.onboarding.utils.interfaces.providers.OnboardingProvider
import com.d4rk.android.libs.apptoolkit.data.client.KtorClient
import com.d4rk.android.libs.apptoolkit.data.core.ads.AdsCoreManager
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.LessonMapper
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.LessonRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.mapper.LessonUiMapper
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.LessonViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.HomeMapper
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.HomeRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.BuildConfigHomeEnvironmentResolver
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.DefaultHomeEndpointProvider
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.HomeEndpointProvider
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.HomeEnvironmentResolver
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.HomeRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.KtorHomeRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.mapper.HomeUiMapper
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.HomeViewModel
import com.d4rk.englishwithlidia.plus.app.main.ui.MainViewModel
import com.d4rk.englishwithlidia.plus.app.onboarding.utils.interfaces.providers.AppOnboardingProvider
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
import com.d4rk.englishwithlidia.plus.core.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule: Module = module {
    single<DataStore> { DataStore(context = get(), dispatchers = get()) }
    single<AdsCoreManager> {
        AdsCoreManager(
            context = get(),
            buildInfoProvider = get(),
            dispatchers = get()
        )
    }
    single { KtorClient.createClient(enableLogging = BuildConfig.DEBUG) }

    single<OnboardingProvider> { AppOnboardingProvider() }

    single<NavigationRepository> { MainRepositoryImpl(dispatchers = get()) }

    viewModel { MainViewModel(navigationRepository = get()) }

    single<String>(qualifier = named("developer_apps_base_url")) { BuildConfig.DEVELOPER_APPS_BASE_URL }

    // Lessons
    single<Json>(qualifier = named("lessons_json_parser")) {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    single { HomeMapper() }
    single<HomeEnvironmentResolver> {
        BuildConfigHomeEnvironmentResolver(isDebugBuild = BuildConfig.DEBUG)
    }
    single<HomeEndpointProvider> {
        DefaultHomeEndpointProvider(
            baseRepositoryUrl = ApiConstants.BASE_REPOSITORY_URL,
            environmentResolver = get(),
        )
    }
    single<HomeRemoteDataSource> {
        KtorHomeRemoteDataSource(
            client = get(),
            endpointProvider = get(),
            jsonParser = get(named("lessons_json_parser")),
        )
    }
    single<HomeRepository> {
        HomeRepositoryImpl(
            dispatchers = get(),
            mapper = get(),
            remoteDataSource = get(),
        )
    }
    factory { GetHomeLessonsUseCase(repository = get()) }
    single { HomeUiMapper() }
    viewModel { HomeViewModel(getHomeLessonsUseCase = get(), uiMapper = get()) }

    single { LessonMapper() }
    single { AudioCacheManager(context = get(), dispatchers = get()) }
    single<LessonRepository> {
        LessonRepositoryImpl(
            client = get(),
            dispatchers = get(),
            mapper = get(),
            audioCache = get(),
            jsonParser = get(named("lessons_json_parser")),
        )
    }
    factory { GetLessonUseCase(repository = get()) }
    single { LessonUiMapper() }
    viewModel { LessonViewModel(getLessonUseCase = get(), uiMapper = get()) }
}
