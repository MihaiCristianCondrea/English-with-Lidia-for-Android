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

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d4rk.android.libs.apptoolkit.core.ui.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.state.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.views.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.views.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.views.layouts.ScreenStateHandler
import com.d4rk.android.libs.apptoolkit.core.ui.views.navigation.LargeTopAppBarWithScaffold
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.LessonContentLayout
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonContentTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonRoute(
    viewModel: LessonViewModel,
    bannerConfig: AdsConfig,
    mediumRectangleConfig: AdsConfig,
    onBack: () -> Unit,
    onPlayClick: () -> Unit,
    onSeek: (Float) -> Unit,
    onPreparePlayer: (UiLessonContent, String, Boolean) -> Unit,
) {
    val screenState: UiStateScreen<UiLessonScreen> by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var preparedContentId by rememberSaveable { mutableStateOf<String?>(null) }
    val currentOnPreparePlayer by rememberUpdatedState(newValue = onPreparePlayer)
    val currentOnPlayClick by rememberUpdatedState(newValue = onPlayClick)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                preparedContentId = null
            }
        }

        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    val handlePlayClick: () -> Unit = {
        val lesson = screenState.data
        val content = lesson?.lessonContent?.firstOrNull {
            it.contentType == LessonContentTypes.CONTENT_PLAYER
        }

        when {
            lesson == null || content == null -> currentOnPlayClick()
            preparedContentId != content.contentId -> {
                currentOnPreparePlayer(content, lesson.lessonTitle, true)
                preparedContentId = content.contentId
            }
            else -> currentOnPlayClick()
        }
    }


    LessonScreen(
        screenState = screenState,
        bannerConfig = bannerConfig,
        mediumRectangleConfig = mediumRectangleConfig,
        onBack = onBack,
        onPlayClick = { handlePlayClick() },
        onSeek = onSeek,
        listState = listState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    screenState: UiStateScreen<UiLessonScreen>,
    bannerConfig: AdsConfig,
    mediumRectangleConfig: AdsConfig,
    onBack: () -> Unit,
    onPlayClick: () -> Unit,
    onSeek: (Float) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
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
}
