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
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.components.LessonListLayout
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
            ListingContent(
                uiListingScreen = uiListingScreen,
                onLessonSelected = onLessonSelected,
                bannerAdsConfig = bannerAdsConfig,
                mediumRectangleAdsConfig = mediumRectangleAdsConfig,
                paddingValues = paddingValues,
                lessonListState = lessonListState,
                modifier = modifier,
            )
        },
    )
}

@Composable
private fun ListingContent(
    uiListingScreen: ListingUiState,
    onLessonSelected: (ListingLessonUiModel) -> Unit,
    bannerAdsConfig: AdsConfig,
    mediumRectangleAdsConfig: AdsConfig,
    paddingValues: PaddingValues,
    lessonListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LessonListLayout(
        lessons = uiListingScreen.lessons,
        bannerAdsConfig = bannerAdsConfig,
        mediumRectangleAdsConfig = mediumRectangleAdsConfig,
        onLessonClick = onLessonSelected,
        paddingValues = paddingValues,
        listState = lessonListState,
        modifier = modifier,
    )
}
