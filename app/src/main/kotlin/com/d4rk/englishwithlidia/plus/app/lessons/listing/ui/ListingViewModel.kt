/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui

import androidx.lifecycle.viewModelScope
import com.d4rk.android.libs.apptoolkit.core.coroutines.dispatchers.DispatcherProvider
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
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.usecases.GetListingLessonsUseCase
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.contract.ListingAction
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.contract.ListingEvent
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.mapper.toUiState
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingUiState
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

class ListingViewModel(
    private val getListingLessonsUseCase: GetListingLessonsUseCase,
    private val dispatchers: DispatcherProvider,
    private val firebaseController: FirebaseController,
) : ScreenViewModel<ListingUiState, ListingEvent, ListingAction>(
    initialState = UiStateScreen(
        screenState = ScreenState.IsLoading(),
        data = ListingUiState(),
    )
) {

    private var loadLessonsJob: Job? = null

    init {
        onEvent(event = ListingEvent.LoadLessons)
    }

    override fun onEvent(event: ListingEvent) {
        when (event) {
            ListingEvent.LoadLessons -> loadLessons()
            ListingEvent.DismissSnackbar -> screenState.dismissSnackbar()
        }
    }

    private fun loadLessons() {
        loadLessonsJob?.cancel()

        loadLessonsJob = getListingLessonsUseCase()
            .flowOn(context = dispatchers.io)
            .onStart { screenState.setLoading() }
            .onEach { result: DataState<ListingScreen, AppErrors> ->
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
                        screenState.updateState(newValues = ScreenState.NoData())
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
                    viewModelName = "ListingViewModel",
                    action = "loadLessons",
                    throwable = it,
                )

                screenState.updateState(newValues = ScreenState.NoData())
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
