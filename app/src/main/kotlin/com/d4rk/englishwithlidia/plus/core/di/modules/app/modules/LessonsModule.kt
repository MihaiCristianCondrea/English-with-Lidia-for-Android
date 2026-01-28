package com.d4rk.englishwithlidia.plus.core.di.modules.app.modules

import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.LessonRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.repository.LessonRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.LessonViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.HomeRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.repository.HomeRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.HomeViewModel
import com.d4rk.englishwithlidia.plus.player.audio.AudioCacheManager
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val lessonsModule: Module = module {

    single<Json>(qualifier = named("lessons_json_parser")) {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    // -----------------------------
    // Home lessons (list)
    // -----------------------------

    single {
        HomeRemoteDataSource(
            client = get(),
            jsonParser = get(named("lessons_json_parser")),
        )
    }

    single<HomeRepository> {
        HomeRepositoryImpl(
            remoteDataSource = get(),
        )
    }

    single { GetHomeLessonsUseCase(repository = get()) }
    viewModel {
        HomeViewModel(
            getHomeLessonsUseCase = get(),
            dispatchers = get(),
            firebaseController = get()
        )
    }

    // -----------------------------
    // Lesson details
    // -----------------------------
    single<AudioCacheManager> { AudioCacheManager(context = get(), dispatchers = get()) }

    single {
        LessonRemoteDataSource(
            client = get(),
            jsonParser = get(named("lessons_json_parser")),
        )
    }

    single<LessonRepository> {
        LessonRepositoryImpl(
            remoteDataSource = get(),
            audioCache = get(),
        )
    }

    single<GetLessonUseCase> { GetLessonUseCase(repository = get()) }

    viewModel {
        LessonViewModel(
            getLessonUseCase = get(),
            dispatchers = get(),
            firebaseController = get(),
        )
    }
}
