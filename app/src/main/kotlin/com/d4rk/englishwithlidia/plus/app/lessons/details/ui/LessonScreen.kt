package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.ScreenStateHandler
import com.d4rk.android.libs.apptoolkit.core.ui.components.navigation.LargeTopAppBarWithScaffold
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.components.LessonContentLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onBack: () -> Unit,
    onPlayClick: () -> Unit,
    onSeekChange: (Float) -> Unit,
) {
    val listState = rememberLazyListState()
    val screenState: UiStateScreen<UiLessonScreen> by viewModel.uiState.collectAsStateWithLifecycle()

    LargeTopAppBarWithScaffold(
        title = screenState.data?.lessonTitle ?: "",
        onBackClicked = onBack
    ) { paddingValues ->

        ScreenStateHandler(
            screenState = screenState,
            onLoading = {
                LoadingScreen()
            },
            onEmpty = {
                NoDataScreen()
            },
            onSuccess = { lesson ->
                LessonContentLayout(
                    paddingValues = paddingValues,
                    listState = listState,
                    lesson = lesson,
                    onPlayClick = onPlayClick,
                    onSeekChange = onSeekChange,
                )
            },
        )
    }
}

