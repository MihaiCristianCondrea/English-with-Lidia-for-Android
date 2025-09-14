package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiTetheringError
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.ScreenStateHandler
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components.LessonListLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val screenState: UiStateScreen<UiHomeScreen> by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        screenState = screenState,
        onRetry = { viewModel.onEvent(event = HomeEvent.FetchLessons) },
        paddingValues = paddingValues,
    )
}


@Composable
fun HomeScreen(
    screenState: UiStateScreen<UiHomeScreen>,
    onRetry: () -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    ScreenStateHandler(
        screenState = screenState,
        onLoading = { LoadingScreen() },
        onEmpty = {
            NoDataScreen(
                icon = Icons.Outlined.WifiTetheringError,
                showRetry = true,
                onRetry = onRetry,
            )
        },
        onSuccess = { uiHomeScreen ->
            LessonListLayout(
                lessons = uiHomeScreen.lessons,
                paddingValues = paddingValues,
                modifier = modifier,
            )
        },
    )
}
