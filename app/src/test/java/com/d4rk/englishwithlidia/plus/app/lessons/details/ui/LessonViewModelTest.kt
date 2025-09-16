package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private typealias TestCoroutineDispatcher = StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class LessonViewModelTest {

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var repository: LessonRepository
    private lateinit var useCase: GetLessonUseCase

    @BeforeEach
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        useCase = GetLessonUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiStateScreen emits success when repository returns lesson`() = runTest {
        val lessonId = "lesson-id"
        val expectedLesson = UiLessonScreen(
            lessonTitle = "Grammar basics",
            lessonContent = listOf(
                UiLessonContent(
                    contentId = "content-id",
                    contentType = "text",
                )
            )
        )
        coEvery { repository.getLesson(lessonId) } returns expectedLesson

        val viewModel = LessonViewModel(useCase)

        viewModel.getLesson(lessonId)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.screenState is ScreenState.Success)
        assertEquals(expectedLesson, state.data)
        coVerify(exactly = 1) { repository.getLesson(lessonId) }
    }

    @Test
    fun `uiStateScreen emits empty when repository returns no content`() = runTest {
        val lessonId = "lesson-id"
        val emptyLesson = UiLessonScreen()
        coEvery { repository.getLesson(lessonId) } returns emptyLesson

        val viewModel = LessonViewModel(useCase)

        viewModel.getLesson(lessonId)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.screenState is ScreenState.NoData)
        assertEquals(emptyLesson, state.data)
        coVerify(exactly = 1) { repository.getLesson(lessonId) }
    }

    @Test
    fun `uiStateScreen emits failure when repository throws`() = runTest {
        val lessonId = "lesson-id"
        coEvery { repository.getLesson(lessonId) } throws IllegalStateException("error")

        val viewModel = LessonViewModel(useCase)

        viewModel.getLesson(lessonId)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.screenState is ScreenState.NoData)
        assertEquals(UiLessonScreen(), state.data)
        coVerify(exactly = 1) { repository.getLesson(lessonId) }
    }

    @Test
    fun `playback events mutate lesson state`() = runTest {
        val viewModel = LessonViewModel(useCase)

        viewModel.updateIsPlaying(true)
        viewModel.updatePlaybackDuration(120L)
        viewModel.updatePlaybackPosition(30L)
        testDispatcher.scheduler.advanceUntilIdle()

        val playbackState = viewModel.uiState.value.data
        assertTrue(playbackState?.isPlaying == true)
        assertEquals(120L, playbackState?.playbackDuration)
        assertEquals(30L, playbackState?.playbackPosition)

        viewModel.onPlaybackError()

        val errorState = viewModel.uiState.value.data
        assertTrue(errorState?.hasPlaybackError == true)
        assertFalse(errorState?.isPlaying ?: true)
    }
}

