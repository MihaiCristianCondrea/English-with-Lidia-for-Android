package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeAction
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.mapper.HomeUiMapper
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val getHomeLessonsUseCase: GetHomeLessonsUseCase,
    private val uiMapper: HomeUiMapper,
) : ScreenViewModel<UiHomeScreen, HomeEvent, HomeAction>(
    initialState = INITIAL_STATE
) {

    private val refreshActions = MutableSharedFlow<HomeEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val lessonsState = refreshActions
        .onStart { emit(HomeEvent.FetchLessons) }
        .flatMapLatest { event ->
            when (event) {
                HomeEvent.FetchLessons -> observeHomeLessons()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = INITIAL_STATE,
        )

    init {
        viewModelScope.launch {
            lessonsState.collect { newState ->
                screenState.update { newState }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        if (!refreshActions.tryEmit(event)) {
            viewModelScope.launch { refreshActions.emit(event) }
        }
    }

    private fun observeHomeLessons(): Flow<UiStateScreen<UiHomeScreen>> =
        getHomeLessonsUseCase()
            .map { homeScreen -> uiMapper.map(homeScreen) }
            .map { uiScreen ->
                if (uiScreen.lessons.isEmpty()) {
                    UiStateScreen(
                        screenState = ScreenState.NoData(),
                        data = UiHomeScreen(),
                    )
                } else {
                    UiStateScreen(
                        screenState = ScreenState.Success(),
                        data = uiScreen,
                    )
                }
            }
            .onStart {
                emit(screenState.value.copy(screenState = ScreenState.IsLoading()))
            }
            .catch { throwable ->
                if (throwable is CancellationException) throw throwable
                emit(UiStateScreen(screenState = ScreenState.NoData(), data = UiHomeScreen()))
            }
            .distinctUntilChanged()

    private companion object {
        private val INITIAL_STATE = UiStateScreen(
            screenState = ScreenState.IsLoading(),
            data = UiHomeScreen(),
        )
    }
}
