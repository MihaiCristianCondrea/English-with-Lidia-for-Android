package com.d4rk.englishwithlidia.plus.app.lessons.list.ui

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
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.contract.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state.HomeLessonUiModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state.HomeUiState
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components.LessonListLayout
import com.d4rk.englishwithlidia.plus.app.main.ui.views.navigation.openLessonDetailActivity
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
) {

    val viewModel: HomeViewModel = koinViewModel()

    val screenState: UiStateScreen<HomeUiState> by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lessonListState = rememberLazyListState()
    val bannerAdsConfig: AdsConfig = koinInject()
    val mediumRectangleAdsConfig: AdsConfig =
        koinInject(qualifier = named(name = "banner_medium_rectangle"))
    val onRetryAction = remember(viewModel) {
        { viewModel.onEvent(event = HomeEvent.LoadLessons) }
    }
    val onLessonSelected: (HomeLessonUiModel) -> Unit = remember(context) {
        { lesson ->
            openLessonDetailActivity(
                context = context,
                lesson = lesson,
            )
        }
    }

    HomeScreen(
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
fun HomeScreen(
    screenState: UiStateScreen<HomeUiState>,
    onRetry: () -> Unit,
    onLessonSelected: (HomeLessonUiModel) -> Unit,
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
        onSuccess = { uiHomeScreen ->
            HomeContent(
                uiHomeScreen = uiHomeScreen,
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
private fun HomeContent(
    uiHomeScreen: HomeUiState,
    onLessonSelected: (HomeLessonUiModel) -> Unit,
    bannerAdsConfig: AdsConfig,
    mediumRectangleAdsConfig: AdsConfig,
    paddingValues: PaddingValues,
    lessonListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LessonListLayout(
        lessons = uiHomeScreen.lessons,
        bannerAdsConfig = bannerAdsConfig,
        mediumRectangleAdsConfig = mediumRectangleAdsConfig,
        onLessonClick = onLessonSelected,
        paddingValues = paddingValues,
        listState = lessonListState,
        modifier = modifier,
    )
}
