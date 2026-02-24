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
    single {
        LessonRemoteDataSource(
            client = get(),
            jsonParser = get(named("lessons_json_parser")),
        )
    }

    single<LessonRepository> {
        LessonRepositoryImpl(
            remoteDataSource = get(),
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
