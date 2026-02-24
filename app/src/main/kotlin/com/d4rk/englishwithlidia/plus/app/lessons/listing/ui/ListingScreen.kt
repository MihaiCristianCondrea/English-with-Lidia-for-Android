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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiTetheringError
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.d4rk.android.libs.apptoolkit.core.ui.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.state.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.views.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.views.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.views.layouts.ScreenStateHandler
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.contract.ListingEvent
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingLessonUiModel
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingUiState
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views.LessonListLayout
import com.d4rk.englishwithlidia.plus.app.main.ui.views.navigation.openLessonDetailActivity
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun ListingRoute(
    paddingValues: PaddingValues,
) {

    val viewModel: ListingViewModel = koinViewModel()

    val screenState: UiStateScreen<ListingUiState> by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lessonListState = rememberLazyListState()
    val bannerAdsConfig: AdsConfig = koinInject()
    val mediumRectangleAdsConfig: AdsConfig =
        koinInject(qualifier = named(name = "banner_medium_rectangle"))
    val onRetryAction = remember(viewModel) {
        { viewModel.onEvent(event = ListingEvent.LoadLessons) }
    }
    val onLessonSelected: (ListingLessonUiModel) -> Unit = remember(context) {
        { lesson ->
            openLessonDetailActivity(
                context = context,
                lesson = lesson,
            )
        }
    }

    ListingScreen(
        screenState = screenState,
        onRetry = onRetryAction,
        onLessonSelected = onLessonSelected,
        bannerAdsConfig = bannerAdsConfig,
        mediumRectangleAdsConfig = mediumRectangleAdsConfig,
        paddingValues = paddingValues,
        lessonListState = lessonListState,
    )
}


@Composable
fun ListingScreen(
    screenState: UiStateScreen<ListingUiState>,
    onRetry: () -> Unit,
    onLessonSelected: (ListingLessonUiModel) -> Unit,
    bannerAdsConfig: AdsConfig,
    mediumRectangleAdsConfig: AdsConfig,
    paddingValues: PaddingValues,
    lessonListState: LazyListState,
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
        onSuccess = { uiListingScreen ->
            LessonListLayout(
                lessons = uiListingScreen.lessons,
                bannerAdsConfig = bannerAdsConfig,
                mediumRectangleAdsConfig = mediumRectangleAdsConfig,
                onLessonClick = onLessonSelected,
                paddingValues = paddingValues,
                listState = lessonListState,
                modifier = modifier,
            )
        },
    )
}