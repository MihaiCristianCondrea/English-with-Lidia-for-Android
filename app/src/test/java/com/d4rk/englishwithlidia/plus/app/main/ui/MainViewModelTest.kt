/*
package com.d4rk.englishwithlidia.plus.app.main.ui

import com.d4rk.android.libs.apptoolkit.app.main.domain.repository.NavigationRepository
import com.d4rk.android.libs.apptoolkit.core.domain.model.navigation.NavigationDrawerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
import org.junit.jupiter.api.Assertions.assertTrue
import io.mockk.mockk

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

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
    fun `navigationDrawerItems is populated when repository emits`() = runTest {
        val items = listOf(mockk<NavigationDrawerItem>())
        val repository = FakeNavigationRepository(flowOf(items))
        val viewModel = MainViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value.data!!
        assertEquals(items, state.navigationDrawerItems)
    }

    @Test
    fun `showSnackbar is set when repository throws`() = runTest {
        val errorMessage = "failed"
        val failingFlow: Flow<List<NavigationDrawerItem>> = flow { throw RuntimeException(errorMessage) }
        val repository = FakeNavigationRepository(failingFlow)
        val viewModel = MainViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value.data!!
        assertTrue(state.showSnackbar)
        assertEquals(errorMessage, state.snackbarMessage)
    }

    private class FakeNavigationRepository(
        private val flow: Flow<List<NavigationDrawerItem>>
    ) : NavigationRepository {
        override fun getNavigationDrawerItems(): Flow<List<NavigationDrawerItem>> = flow
    }
}

*/
