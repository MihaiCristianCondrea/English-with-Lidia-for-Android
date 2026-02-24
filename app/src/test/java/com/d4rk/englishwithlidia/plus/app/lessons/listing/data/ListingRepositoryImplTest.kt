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

package com.d4rk.englishwithlidia.plus.app.lessons.listing.data

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.ListingDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model.ListingLessonDto
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model.ListingLessonsResponseDto
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.repository.ListingRepositoryImpl
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingLesson
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class ListingRepositoryImplTest {

    @Test
    fun `fetchListingLessons emits mapped ListingScreen when data source returns payload`() = runTest {
        val dataSource: ListingDataSource = mockk()
        val response = ListingLessonsResponseDto(
            data = listOf(
                ListingLessonDto(
                    lessonId = "1",
                    lessonTitle = "Lesson 1",
                    lessonType = "video",
                    lessonThumbnailImageUrl = "url1",
                    lessonDeepLinkPath = "path1",
                )
            )
        )
        coEvery { dataSource.fetchListingLessons(any()) } returns response
        val repository = ListingRepositoryImpl(remoteDataSource = dataSource)

        val result = repository.fetchListingLessons().first()

        val expected = ListingScreen(
            lessons = listOf(
                ListingLesson(
                    lessonId = "1",
                    lessonTitle = "Lesson 1",
                    lessonType = "video",
                    lessonThumbnailImageUrl = "url1",
                    lessonDeepLinkPath = "path1",
                )
            )
        )

        when (result) {
            is DataState.Success -> assertThat(result.data).isEqualTo(expected)
            else -> fail("Expected Success, got $result")
        }
    }

    @Test
    fun `fetchListingLessons emits parse error when data source throws SerializationException`() = runTest {
        val dataSource: ListingDataSource = mockk()
        coEvery { dataSource.fetchListingLessons(any()) } throws SerializationException("boom")
        val repository = ListingRepositoryImpl(remoteDataSource = dataSource)

        val result = repository.fetchListingLessons().first()

        when (result) {
            is DataState.Error -> assertThat(result.error)
                .isEqualTo(AppErrors.UseCase.FAILED_TO_PARSE_LESSONS)
            else -> fail("Expected Error, got $result")
        }
    }
}
