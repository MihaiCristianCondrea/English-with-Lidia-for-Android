package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.mapper.HomeUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue

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
    }

    @Test
    fun `state is loading while lessons are fetched`() = runTest {
        val flow = MutableSharedFlow<HomeScreen>()
        val useCase = GetHomeLessonsUseCase(FakeHomeRepository(flow))
        val viewModel = HomeViewModel(useCase, HomeUiMapper())

        assertTrue(viewModel.uiState.value.screenState is ScreenState.IsLoading)
    }

    @Test
    fun `state is success when lessons available`() = runTest {
        val lesson = HomeLesson(lessonId = "1", lessonTitle = "Title", lessonType = "video", lessonThumbnailImageUrl = "", lessonDeepLinkPath = "")
        val flow = flowOf(HomeScreen(lessons = listOf(lesson)))
        val useCase = GetHomeLessonsUseCase(FakeHomeRepository(flow))
        val viewModel = HomeViewModel(useCase, HomeUiMapper())

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.Success)
        val data = state.data
        assertNotNull(data)
        assertEquals(1, data!!.lessons.size)
    }

    @Test
    fun `state is no data when lessons list is empty`() = runTest {
        val flow = flowOf(HomeScreen())
        val useCase = GetHomeLessonsUseCase(FakeHomeRepository(flow))
        val viewModel = HomeViewModel(useCase, HomeUiMapper())

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.screenState is ScreenState.NoData)
    }

    @Test
    fun `state is no data when lessons fetch fails`() = runTest {
        val flow: Flow<HomeScreen> = flow { throw RuntimeException() }
        val useCase = GetHomeLessonsUseCase(FakeHomeRepository(flow))
        val viewModel = HomeViewModel(useCase, HomeUiMapper())

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.screenState is ScreenState.NoData)
        assertTrue(state.data.lessons.isEmpty())
    }

    private class FakeHomeRepository(
        private val flow: Flow<HomeScreen>
    ) : HomeRepository {
        override fun getHomeLessons(): Flow<HomeScreen> = flow
    }
}

