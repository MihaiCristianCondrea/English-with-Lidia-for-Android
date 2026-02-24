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

package com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.repository

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.Errors
import com.d4rk.android.libs.apptoolkit.core.utils.constants.api.ApiEnvironments
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.errors.toError
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.mapper.firstLessonOrNull
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.LessonRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.EnglishWithLidiaApiEndpoints
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException

class LessonRepositoryImpl(
    private val remoteDataSource: LessonRemoteDataSource,
) : LessonRepository {

    override fun getLesson(lessonId: String): Flow<DataState<Lesson, AppErrors>> =
        flow<DataState<Lesson, AppErrors>> {
            if (lessonId.isBlank()) {
                emit(DataState.Error(error = AppErrors.UseCase.INVALID_LESSON_ID))
                return@flow
            }

            val environment =
                if (BuildConfig.DEBUG) ApiEnvironments.ENV_DEBUG else ApiEnvironments.ENV_RELEASE

            val urlString = EnglishWithLidiaApiEndpoints.lessonDetails(
                environment = environment,
                lessonId = lessonId,
            )

            val response = remoteDataSource.fetchLesson(urlString = urlString)
            val lesson = response.firstLessonOrNull()

            if (lesson == null) {
                emit(DataState.Error(error = AppErrors.UseCase.LESSON_NOT_FOUND))
                return@flow
            }

            emit(DataState.Success(data = lesson))
        }.catch { throwable ->
            if (throwable is CancellationException) throw throwable

            val error: AppErrors = when (throwable) {
                is SerializationException -> AppErrors.UseCase.FAILED_TO_PARSE_LESSON
                else -> AppErrors.Common(
                    throwable.toError(default = Errors.Network.UNKNOWN)
                )
            }

            emit(DataState.Error(error = error))
        }

}
