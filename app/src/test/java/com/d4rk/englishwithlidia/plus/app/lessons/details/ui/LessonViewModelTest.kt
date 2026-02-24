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

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import com.d4rk.android.libs.apptoolkit.core.coroutines.dispatchers.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.android.libs.apptoolkit.core.domain.repository.FirebaseController
import com.d4rk.android.libs.apptoolkit.core.ui.state.ScreenState
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LessonViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers: DispatcherProvider = mockk {
        every { io } returns testDispatcher
        every { main } returns testDispatcher
        every { default } returns testDispatcher
    }
    private val firebaseController: FirebaseController = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state is success when lesson available`() = runTest {
        val useCase = fakeGetLessonUseCase(ResultType.SUCCESS)
        val viewModel = LessonViewModel(useCase, dispatchers, firebaseController)

        viewModel.getLesson("1")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.Success)
        val data = state.data
        assertNotNull(data)
        assertEquals("Title", data!!.lessonTitle)
        assertEquals(1, data.lessonContent.size)
    }

    @Test
    fun `state is no data when lesson empty`() = runTest {
        val useCase = fakeGetLessonUseCase(ResultType.EMPTY)
        val viewModel = LessonViewModel(useCase, dispatchers, firebaseController)

        viewModel.getLesson("1")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.NoData)
        val data = state.data
        assertNotNull(data)
        assertTrue(data!!.lessonContent.isEmpty())
    }

    @Test
    fun `state is no data when use case returns error`() = runTest {
        val useCase = fakeGetLessonUseCase(ResultType.ERROR)
        val viewModel = LessonViewModel(useCase, dispatchers, firebaseController)

        viewModel.getLesson("1")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.screenState is ScreenState.NoData)
    }

    @Test
    fun `playback fields update correctly`() = runTest {
        val useCase = fakeGetLessonUseCase(ResultType.SUCCESS)
        val viewModel = LessonViewModel(useCase, dispatchers, firebaseController)

        viewModel.updateIsPlaying(true)
        viewModel.updatePlaybackDuration(100L)
        viewModel.updatePlaybackPosition(50L)
        advanceUntilIdle()

        val data = viewModel.uiState.value.data
        assertNotNull(data)
        assertTrue(data!!.isPlaying)
        assertEquals(100L, data.playbackDuration)
        assertEquals(50L, data.playbackPosition)
    }

    private enum class ResultType { SUCCESS, EMPTY, ERROR }

    private fun fakeGetLessonUseCase(result: ResultType): GetLessonUseCase {
        val repository = object : LessonRepository {
            override fun getLesson(lessonId: String): Flow<DataState<Lesson, AppErrors>> =
                when (result) {
                    ResultType.SUCCESS -> flowOf(DataState.Success(Lesson(lessonTitle = "Title", lessonContent = listOf(LessonContent(contentId = "1")))))
                    ResultType.EMPTY -> flowOf(DataState.Success(Lesson(lessonTitle = "Title")))
                    ResultType.ERROR -> flowOf(DataState.Error(error = AppErrors.UseCase.LESSON_NOT_FOUND))
                }
        }
        return GetLessonUseCase(repository)
    }
}
