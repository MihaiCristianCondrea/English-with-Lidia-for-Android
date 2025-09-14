package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiTetheringError
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.englishwithlidia.plus.app.main.ui.components.WavyLoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.ScreenStateHandler
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components.LessonListLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    paddingValues : PaddingValues
) {
    val viewModel : HomeViewModel = koinViewModel()
    val screenState: UiStateScreen<UiHomeScreen> by viewModel.uiState.collectAsState()

    ScreenStateHandler(
        screenState = screenState,
        onLoading = {
            WavyLoadingScreen()
        },
        onEmpty = {
            NoDataScreen(
                icon = Icons.Outlined.WifiTetheringError,
                showRetry = true, onRetry = {
                viewModel.onEvent(event = HomeEvent.FetchLessons)
            })
        },
        onSuccess = { uiHomeScreen ->
            LessonListLayout(lessons = uiHomeScreen.lessons,paddingValues)
        },
    )
}