package com.d4rk.englishwithlidia.plus.core.di.modules.app.modules

import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.LessonRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.repository.LessonRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.LessonViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.KtorListingDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.ListingDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.repository.ListingRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.repository.ListingRepository
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.usecases.GetListingLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.ListingViewModel
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
    // Listing lessons
    // -----------------------------

    single<ListingDataSource> {
        KtorListingDataSource(
            client = get(),
            jsonParser = get(named("lessons_json_parser")),
        )
    }

    single<ListingRepository> {
        ListingRepositoryImpl(
            remoteDataSource = get(),
        )
    }

    single { GetListingLessonsUseCase(repository = get()) }
    viewModel {
        ListingViewModel(
            getListingLessonsUseCase = get(),
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
