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

/*
package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui

import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.contract.ListingEvent
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.mapper.ListingUiMapper
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingLesson
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.UiListingLesson
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingUiState
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.repository.ListingRepository
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.usecases.GetListingLessonsUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListingViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `init triggers initial lessons fetch`() = runTest {
        val repository = mockk<ListingRepository>()
        every { repository.getListingLessons() } returns flowOf(ListingScreen())

        ListingViewModel(GetListingLessonsUseCase(repository), ListingUiMapper())
        advanceUntilIdle()

        verify(exactly = 1) { repository.getListingLessons() }
    }

    @Test
    fun `fetch event triggers lessons reload`() = runTest {
        val repository = mockk<ListingRepository>()
        every { repository.getListingLessons() } returns flowOf(ListingScreen())

        val viewModel = ListingViewModel(GetListingLessonsUseCase(repository), ListingUiMapper())
        advanceUntilIdle()

        viewModel.onEvent(ListingEvent.FetchLessons)
        advanceUntilIdle()

        verify(exactly = 2) { repository.getListingLessons() }
    }

    @Test
    fun `uiState is success when repository returns lessons`() = runTest {
        val repository = mockk<ListingRepository>()
        val lesson = ListingLesson(
            lessonId = "1",
            lessonTitle = "Sample lesson",
            lessonType = "video",
            lessonThumbnailImageUrl = "https://example.com/thumb.jpg",
            lessonDeepLinkPath = "/lesson/1",
        )
        every { repository.getListingLessons() } returns flowOf(ListingScreen(lessons = listOf(lesson)))

        val viewModel = ListingViewModel(GetListingLessonsUseCase(repository), ListingUiMapper())
        advanceUntilIdle()

        val expectedState = UiStateScreen(
            screenState = ScreenState.Success(),
            data = ListingUiState(
                lessons = listOf(
                    UiListingLesson(
                        lessonId = lesson.lessonId,
                        lessonTitle = lesson.lessonTitle,
                        lessonType = lesson.lessonType,
                        lessonThumbnailImageUrl = lesson.lessonThumbnailImageUrl,
                        lessonDeepLinkPath = lesson.lessonDeepLinkPath,
                    ),
                ),
            ),
        )

        val state = viewModel.uiState.value
        assertEquals(expectedState, state)
        verify(exactly = 1) { repository.getListingLessons() }
    }

    @Test
    fun `uiState is no data when repository returns empty lessons`() = runTest {
        val repository = mockk<ListingRepository>()
        every { repository.getListingLessons() } returns flowOf(ListingScreen())

        val viewModel = ListingViewModel(GetListingLessonsUseCase(repository), ListingUiMapper())
        advanceUntilIdle()

        val expectedState = UiStateScreen(
            screenState = ScreenState.NoData(),
            data = ListingUiState(),
        )

        val state = viewModel.uiState.value
        assertEquals(expectedState, state)
        verify(exactly = 1) { repository.getListingLessons() }
    }

    @Test
    fun `uiState is no data when repository throws`() = runTest {
        val repository = mockk<ListingRepository>()
        every { repository.getListingLessons() } returns flow {
            throw IllegalStateException("boom")
        }

        val viewModel = ListingViewModel(GetListingLessonsUseCase(repository), ListingUiMapper())
        advanceUntilIdle()

        val expectedState = UiStateScreen(
            screenState = ScreenState.NoData(),
            data = ListingUiState(),
        )

        val state = viewModel.uiState.value
        assertEquals(expectedState, state)
        assertTrue(state.screenState is ScreenState.NoData)
        verify(exactly = 1) { repository.getListingLessons() }
    }
}
*/
