package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.onFailure
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.onSuccess
import com.d4rk.android.libs.apptoolkit.core.domain.repository.FirebaseController
import com.d4rk.android.libs.apptoolkit.core.ui.base.ScreenViewModel
import com.d4rk.android.libs.apptoolkit.core.ui.state.ScreenState
import com.d4rk.android.libs.apptoolkit.core.ui.state.UiSnackbar
import com.d4rk.android.libs.apptoolkit.core.ui.state.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.state.copyData
import com.d4rk.android.libs.apptoolkit.core.ui.state.dismissSnackbar
import com.d4rk.android.libs.apptoolkit.core.ui.state.setLoading
import com.d4rk.android.libs.apptoolkit.core.ui.state.showSnackbar
import com.d4rk.android.libs.apptoolkit.core.ui.state.updateState
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.ScreenMessageType
import com.d4rk.android.libs.apptoolkit.core.utils.platform.UiTextHelper
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases.GetLessonUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.contract.LessonAction
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.contract.LessonEvent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.mappers.toUiModel
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import com.d4rk.englishwithlidia.plus.core.utils.extensions.toErrorMessage
import com.d4rk.englishwithlidia.plus.player.PlaybackEventHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class LessonViewModel(
    private val getLessonUseCase: GetLessonUseCase,
    private val dispatchers: DispatcherProvider,
    private val firebaseController: FirebaseController,
) : ScreenViewModel<UiLessonScreen, LessonEvent, LessonAction>(
    initialState = UiStateScreen(
        screenState = ScreenState.IsLoading(),
        data = UiLessonScreen(),
    )
), PlaybackEventHandler {

    private var fetchLessonJob: Job? = null

    fun getLesson(lessonId: String) {
        onEvent(LessonEvent.FetchLesson(lessonId))
    }

    override fun onEvent(event: LessonEvent) {
        when (event) {
            is LessonEvent.FetchLesson -> fetchLesson(event.lessonId)
            LessonEvent.DismissSnackbar -> screenState.dismissSnackbar()
        }
    }

    private fun fetchLesson(lessonId: String) {
        fetchLessonJob?.cancel()

        fetchLessonJob = getLessonUseCase(lessonId)
            .flowOn(context = dispatchers.io)
            .onStart { screenState.setLoading() }
            .onEach { result: DataState<Lesson, AppErrors> ->
                result
                    .onSuccess { lesson ->
                        val ui = lesson.toUiModel()
                        val newScreenState: ScreenState =
                            if (ui.lessonContent.isEmpty()) ScreenState.NoData() else ScreenState.Success()

                        screenState.update { current ->
                            current.copy(screenState = newScreenState, data = ui)
                        }
                    }
                    .onFailure { error: AppErrors ->
                        screenState.updateState(newValues = ScreenState.Error())
                        screenState.showSnackbar(
                            UiSnackbar(
                                message = error.toErrorMessage(),
                                isError = true,
                                timeStamp = System.currentTimeMillis(),
                                type = ScreenMessageType.SNACKBAR,
                            )
                        )
                    }
            }
            .catch {
                if (it is CancellationException) throw it

                firebaseController.reportViewModelError(
                    viewModelName = "LessonViewModel",
                    action = "fetchLesson",
                    throwable = it,
                )

                screenState.updateState(newValues = ScreenState.Error())
                screenState.showSnackbar(
                    UiSnackbar(
                        message = UiTextHelper.StringResource(R.string.error_failed_to_load_lesson),
                        isError = true,
                        timeStamp = System.currentTimeMillis(),
                        type = ScreenMessageType.SNACKBAR,
                    )
                )
            }
            .launchIn(scope = viewModelScope)
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
