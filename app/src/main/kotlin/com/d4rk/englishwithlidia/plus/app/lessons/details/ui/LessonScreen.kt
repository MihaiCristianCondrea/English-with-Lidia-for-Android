package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.ScreenStateHandler
import com.d4rk.android.libs.apptoolkit.core.ui.components.navigation.LargeTopAppBarWithScaffold
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.components.LessonContentLayout
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonContentTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    bannerConfig: AdsConfig,
    mediumRectangleConfig: AdsConfig,
    onBack: () -> Unit,
    onPlayClick: () -> Unit,
    onSeek: (Float) -> Unit,
    onPreparePlayer: (UiLessonContent, String) -> Unit,
) {
    val listState = rememberLazyListState()
    val screenState: UiStateScreen<UiLessonScreen> by viewModel.uiState.collectAsStateWithLifecycle()
    var isPlayerPrepared by remember { mutableStateOf(false) }

    LaunchedEffect(screenState.data?.lessonContent) {
        screenState.data?.let { lesson ->
            val content =
                lesson.lessonContent.firstOrNull { it.contentType == LessonContentTypes.CONTENT_PLAYER }
            if (!isPlayerPrepared && content != null) {
                onPreparePlayer(content, lesson.lessonTitle)
                isPlayerPrepared = true
            }
        }
    }

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
                    bannerConfig = bannerConfig,
                    mediumRectangleConfig = mediumRectangleConfig,
                    onPlayClick = onPlayClick,
                    onSeek = onSeek,
                )
            },
        )
    }
}

