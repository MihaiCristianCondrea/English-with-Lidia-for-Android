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
import com.d4rk.android.libs.apptoolkit.core.domain.model.ui.UiStateScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.LoadingScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.NoDataScreen
import com.d4rk.android.libs.apptoolkit.core.ui.components.layouts.ScreenStateHandler
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.action.HomeEvent
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components.LessonListLayout
import com.d4rk.englishwithlidia.plus.app.main.ui.components.navigation.openLessonDetailActivity
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val screenState: UiStateScreen<UiHomeScreen> by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lessonListState = rememberLazyListState()
    val bannerAdsConfig: AdsConfig = koinInject()
    val mediumRectangleAdsConfig: AdsConfig =
        koinInject(qualifier = named(name = "banner_medium_rectangle"))
    val onRetryAction = remember(viewModel) {
        { viewModel.onEvent(event = HomeEvent.FetchLessons) }
    }
    val onLessonSelected: (UiHomeLesson) -> Unit = remember(context) {
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
    screenState: UiStateScreen<UiHomeScreen>,
    onRetry: () -> Unit,
    onLessonSelected: (UiHomeLesson) -> Unit,
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
    uiHomeScreen: UiHomeScreen,
    onLessonSelected: (UiHomeLesson) -> Unit,
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
