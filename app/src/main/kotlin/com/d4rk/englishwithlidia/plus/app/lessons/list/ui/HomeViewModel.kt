package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeAction
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeLessonsUseCase: GetHomeLessonsUseCase,
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
        viewModelScope.launch {
            getHomeLessonsUseCase()
                .catch { throwable ->
                    if (throwable is CancellationException) throw throwable
                    screenState.update { current ->
                        current.copy(screenState = ScreenState.NoData(), data = UiHomeScreen())
                    }
                }
                .collect { homeScreen ->
                    val uiScreen = homeScreen.toUi()
                    if (uiScreen.lessons.isEmpty()) {
                        screenState.update { current ->
                            current.copy(screenState = ScreenState.NoData(), data = UiHomeScreen())
                        }
                    } else {
                        screenState.update { current ->
                            current.copy(screenState = ScreenState.Success(), data = uiScreen)
                        }
                    }
                }
        }
    }
}

private fun HomeScreen.toUi(): UiHomeScreen =
    UiHomeScreen(lessons = lessons.map { it.toUi() })

private fun HomeLesson.toUi(): UiHomeLesson =
    UiHomeLesson(
        lessonId = lessonId,
        lessonTitle = lessonTitle,
        lessonType = lessonType,
        lessonThumbnailImageUrl = lessonThumbnailImageUrl,
        lessonDeepLinkPath = lessonDeepLinkPath,
    )
