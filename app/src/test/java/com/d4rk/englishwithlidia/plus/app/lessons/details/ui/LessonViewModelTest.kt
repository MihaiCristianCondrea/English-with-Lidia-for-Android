package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        val viewModel = LessonViewModel(useCase)

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
        val viewModel = LessonViewModel(useCase)

        viewModel.getLesson("1")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.NoData)
        val data = state.data
        assertNotNull(data)
        assertTrue(data!!.lessonContent.isEmpty())
    }

    @Test
    fun `state is no data when use case throws`() = runTest {
        val useCase = fakeGetLessonUseCase(ResultType.ERROR)
        val viewModel = LessonViewModel(useCase)

        viewModel.getLesson("1")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.screenState is ScreenState.NoData)
    }

    @Test
    fun `playback fields update correctly`() = runTest {
        val useCase = fakeGetLessonUseCase(ResultType.SUCCESS)
        val viewModel = LessonViewModel(useCase)

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
            override suspend fun getLesson(lessonId: String): UiLessonScreen {
                return when (result) {
                    ResultType.SUCCESS -> UiLessonScreen(
                        lessonTitle = "Title",
                        lessonContent = listOf(
                            UiLessonContent(contentId = "1")
                        )
                    )
                    ResultType.EMPTY -> UiLessonScreen()
                    ResultType.ERROR -> throw RuntimeException()
                }
            }
        }
        return GetLessonUseCase(repository)
    }
}

