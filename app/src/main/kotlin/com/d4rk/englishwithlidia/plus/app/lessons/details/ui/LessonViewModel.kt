package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.ScreenState
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.copyData
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.setLoading
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action.LessonAction
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.action.LessonEvent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.mapper.LessonUiMapper
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.player.PlaybackEventHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LessonViewModel(
    private val getLessonUseCase: GetLessonUseCase,
    private val uiMapper: LessonUiMapper,
) : ScreenViewModel<UiLessonScreen, LessonEvent, LessonAction>(
    initialState = UiStateScreen(screenState = ScreenState.IsLoading(), data = UiLessonScreen())
), PlaybackEventHandler {

    private var fetchLessonJob: Job? = null

    fun getLesson(lessonId: String) {
        onEvent(LessonEvent.FetchLesson(lessonId))
    }

    private fun fetchLesson(lessonId: String) {
        fetchLessonJob?.cancel()
        fetchLessonJob = viewModelScope.launch {
            getLessonUseCase(lessonId)
                .onStart { screenState.setLoading() }
                .catch {
                    screenState.update { current ->
                        current.copy(screenState = ScreenState.NoData(), data = UiLessonScreen())
                    }
                }
                .collectLatest { lesson ->
                    screenState.update { current ->
                        val data = lesson?.let { domainLesson ->
                            if (domainLesson.lessonContent.isEmpty()) {
                                null
                            } else {
                                uiMapper.map(domainLesson)
                            }
                        }

                        if (data == null) {
                            current.copy(screenState = ScreenState.NoData(), data = UiLessonScreen())
                        } else {
                            current.copy(screenState = ScreenState.Success(), data = data)
                        }
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

    override fun updateIsBuffering(isBuffering: Boolean) {
        screenState.copyData { copy(isBuffering = isBuffering) }
    }

    override fun updatePlaybackDuration(duration: Long) {
        screenState.copyData { copy(playbackDuration = duration) }
    }

    override fun updatePlaybackPosition(position: Long) {
        screenState.copyData { copy(playbackPosition = position) }
    }

    override fun onPlaybackError() {
        screenState.copyData {
            copy(hasPlaybackError = true, isPlaying = false, isBuffering = false)
        }
    }
}
