package com.d4rk.englishwithlidia.plus.app.lessons.details.ui


import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.copyData
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.setLoading
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action.LessonAction
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action.LessonEvent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import com.d4rk.englishwithlidia.plus.app.player.PlaybackEventHandler
import kotlinx.coroutines.launch

class LessonViewModel(
    private val getLessonUseCase: GetLessonUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ScreenViewModel<UiLessonScreen, LessonEvent, LessonAction>(
    initialState = UiStateScreen(screenState = ScreenState.IsLoading(), data = UiLessonScreen())
), PlaybackEventHandler {

    init {
        // No player initialization here. Player lifecycle is handled by ActivityPlayer.
    }

    fun getLesson(lessonId: String) {
        onEvent(LessonEvent.FetchLesson(lessonId))
    }

    private fun fetchLesson(lessonId: String) {
        viewModelScope.launch {
            screenState.setLoading<UiLessonScreen>()
            val lesson = getLessonUseCase(lessonId)
            withContext(dispatcherProvider.main) {
                screenState.update { current ->
                    current.copy(screenState = ScreenState.Success(), data = lesson)
                }
            }
        }
    }

    override fun onEvent(event: LessonEvent) {
        when (event) {
            is LessonEvent.FetchLesson -> fetchLesson(event.lessonId)
        }
    }

    override fun updateIsPlaying(isPlaying: Boolean) {
        screenState.copyData { copy(isPlaying = isPlaying) }
    }

    override fun updatePlaybackDuration(duration: Long) {
        screenState.copyData { copy(playbackDuration = duration) }
    }

    override fun updatePlaybackPosition(position: Long) {
        screenState.copyData { copy(playbackPosition = position) }
    }
}
