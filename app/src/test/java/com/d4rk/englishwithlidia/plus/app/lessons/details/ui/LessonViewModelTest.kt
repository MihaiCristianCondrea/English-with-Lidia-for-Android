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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

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
        val lesson = UiLessonScreen(
            lessonTitle = "title",
            lessonContent = listOf(UiLessonContent(contentId = "1"))
        )
        val viewModel = LessonViewModel(GetLessonUseCase(SuccessLessonRepository(lesson)))

        viewModel.getLesson("1")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.Success)
        assertEquals(1, state.data.lessonContent.size)
    }

    @Test
    fun `state is no data when lesson content empty`() = runTest {
        val viewModel = LessonViewModel(GetLessonUseCase(SuccessLessonRepository(UiLessonScreen())))

        viewModel.getLesson("1")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.NoData)
        assertTrue(state.data.lessonContent.isEmpty())
    }

    @Test
    fun `state is no data when lesson fetch fails`() = runTest {
        val viewModel = LessonViewModel(GetLessonUseCase(FailingLessonRepository()))

        viewModel.getLesson("1")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.NoData)
        assertTrue(state.data.lessonContent.isEmpty())
    }

    @Test
    fun `update playback info changes state`() = runTest {
        val viewModel = LessonViewModel(GetLessonUseCase(SuccessLessonRepository(UiLessonScreen())))

        viewModel.updateIsPlaying(true)
        viewModel.updatePlaybackDuration(1000L)
        viewModel.updatePlaybackPosition(500L)

        val data = viewModel.uiState.value.data
        assertTrue(data.isPlaying)
        assertEquals(1000L, data.playbackDuration)
        assertEquals(500L, data.playbackPosition)
    }

    private class SuccessLessonRepository(
        private val lesson: UiLessonScreen
    ) : LessonRepository {
        override suspend fun getLesson(lessonId: String): UiLessonScreen = lesson
    }

    private class FailingLessonRepository : LessonRepository {
        override suspend fun getLesson(lessonId: String): UiLessonScreen {
            throw RuntimeException()
        }
    }
}

