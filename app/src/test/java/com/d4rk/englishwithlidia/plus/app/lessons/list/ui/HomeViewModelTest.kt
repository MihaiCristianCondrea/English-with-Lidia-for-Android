package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.mapper.HomeUiMapper
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
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
class HomeViewModelTest {

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
        val repository = mockk<HomeRepository>()
        every { repository.getHomeLessons() } returns flowOf(HomeScreen())

        HomeViewModel(GetHomeLessonsUseCase(repository), HomeUiMapper())
        advanceUntilIdle()

        verify(exactly = 1) { repository.getHomeLessons() }
    }

    @Test
    fun `uiState is success when repository returns lessons`() = runTest {
        val repository = mockk<HomeRepository>()
        val lesson = HomeLesson(
            lessonId = "1",
            lessonTitle = "Sample lesson",
            lessonType = "video",
            lessonThumbnailImageUrl = "https://example.com/thumb.jpg",
            lessonDeepLinkPath = "/lesson/1",
        )
        every { repository.getHomeLessons() } returns flowOf(HomeScreen(lessons = listOf(lesson)))

        val viewModel = HomeViewModel(GetHomeLessonsUseCase(repository), HomeUiMapper())
        advanceUntilIdle()

        val expectedState = UiStateScreen(
            screenState = ScreenState.Success(),
            data = UiHomeScreen(
                lessons = listOf(
                    UiHomeLesson(
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
        verify(exactly = 1) { repository.getHomeLessons() }
    }

    @Test
    fun `uiState is no data when repository returns empty lessons`() = runTest {
        val repository = mockk<HomeRepository>()
        every { repository.getHomeLessons() } returns flowOf(HomeScreen())

        val viewModel = HomeViewModel(GetHomeLessonsUseCase(repository), HomeUiMapper())
        advanceUntilIdle()

        val expectedState = UiStateScreen(
            screenState = ScreenState.NoData(),
            data = UiHomeScreen(),
        )

        val state = viewModel.uiState.value
        assertEquals(expectedState, state)
        verify(exactly = 1) { repository.getHomeLessons() }
    }

    @Test
    fun `uiState is no data when repository throws`() = runTest {
        val repository = mockk<HomeRepository>()
        every { repository.getHomeLessons() } returns flow {
            throw IllegalStateException("boom")
        }

        val viewModel = HomeViewModel(GetHomeLessonsUseCase(repository), HomeUiMapper())
        advanceUntilIdle()

        val expectedState = UiStateScreen(
            screenState = ScreenState.NoData(),
            data = UiHomeScreen(),
        )

        val state = viewModel.uiState.value
        assertEquals(expectedState, state)
        assertTrue(state.screenState is ScreenState.NoData)
        verify(exactly = 1) { repository.getHomeLessons() }
    }

    @Test
    fun `retry sets state to loading before new emission`() = runTest {
        val repository = mockk<HomeRepository>()
        val sharedFlow = MutableSharedFlow<HomeScreen>()
        every { repository.getHomeLessons() } returns sharedFlow

        val viewModel = HomeViewModel(GetHomeLessonsUseCase(repository), HomeUiMapper())
        advanceUntilIdle()

        sharedFlow.emit(HomeScreen())
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.screenState is ScreenState.NoData)

        viewModel.onEvent(HomeEvent.FetchLessons)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.screenState is ScreenState.IsLoading)

        val lesson = HomeLesson(
            lessonId = "1",
            lessonTitle = "Sample lesson",
            lessonType = "video",
            lessonThumbnailImageUrl = "https://example.com/thumb.jpg",
            lessonDeepLinkPath = "/lesson/1",
        )
        sharedFlow.emit(HomeScreen(lessons = listOf(lesson)))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.Success)
        assertEquals(1, state.data.lessons.size)
        verify(exactly = 2) { repository.getHomeLessons() }
    }
}
