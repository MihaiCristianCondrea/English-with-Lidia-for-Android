package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

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
import com.d4rk.android.libs.apptoolkit.core.ui.state.dismissSnackbar
import com.d4rk.android.libs.apptoolkit.core.ui.state.setLoading
import com.d4rk.android.libs.apptoolkit.core.ui.state.showSnackbar
import com.d4rk.android.libs.apptoolkit.core.ui.state.updateState
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.ScreenMessageType
import com.d4rk.android.libs.apptoolkit.core.utils.platform.UiTextHelper
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases.GetHomeLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.contract.HomeAction
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.contract.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.mapper.toUiState
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state.HomeUiState
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import com.d4rk.englishwithlidia.plus.core.utils.extensions.toErrorMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val getHomeLessonsUseCase: GetHomeLessonsUseCase,
    private val dispatchers: DispatcherProvider,
    private val firebaseController: FirebaseController,
) : ScreenViewModel<HomeUiState, HomeEvent, HomeAction>(
    initialState = UiStateScreen(
        screenState = ScreenState.IsLoading(),
        data = HomeUiState(),
    )
) {

    private var loadLessonsJob: Job? = null

    init {
        onEvent(event = HomeEvent.LoadLessons)
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadLessons -> loadLessons()
            HomeEvent.DismissSnackbar -> screenState.dismissSnackbar()
        }
    }

    private fun loadLessons() {
        loadLessonsJob?.cancel()

        loadLessonsJob = getHomeLessonsUseCase()
            .flowOn(context = dispatchers.io)
            .onStart { screenState.setLoading() }
            .onEach { result: DataState<HomeScreen, AppErrors> ->
                result
                    .onSuccess { screen ->
                        val uiState = screen.toUiState()
                        val screenStateForData: ScreenState =
                            if (uiState.lessons.isEmpty()) ScreenState.NoData() else ScreenState.Success()

                        screenState.update { current ->
                            current.copy(
                                screenState = screenStateForData,
                                data = uiState,
                            )
                        }
                    }
                    .onFailure { error ->
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
                    viewModelName = "HomeViewModel",
                    action = "loadLessons",
                    throwable = it,
                )

                screenState.updateState(newValues = ScreenState.Error())
                screenState.showSnackbar(
                    UiSnackbar(
                        message = UiTextHelper.StringResource(R.string.error_failed_to_load_lessons),
                        isError = true,
                        timeStamp = System.currentTimeMillis(),
                        type = ScreenMessageType.SNACKBAR,
                    )
                )
            }
            .launchIn(scope = viewModelScope)
    }
}
