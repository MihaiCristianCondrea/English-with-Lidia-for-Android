package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeAction
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getHomeLessonsUseCase: GetHomeLessonsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ScreenViewModel<UiHomeScreen, HomeEvent, HomeAction>(
    initialState = UiStateScreen(screenState = ScreenState.IsLoading(), data = UiHomeScreen())
) {

    init {
        onEvent(HomeEvent.FetchLessons)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchLessons -> getHomeLessons()
        }
    }

    private fun getHomeLessons() {
        viewModelScope.launch(context = dispatcherProvider.io) {
            val lessons = getHomeLessonsUseCase()
            withContext(dispatcherProvider.main) {
                if (lessons.lessons.isEmpty()) {
                    screenState.update { current ->
                        current.copy(screenState = ScreenState.NoData(), data = lessons)
                    }
                } else {
                    screenState.update { current ->
                        current.copy(screenState = ScreenState.Success(), data = lessons)
                    }
                }
            }
        }
    }
}
