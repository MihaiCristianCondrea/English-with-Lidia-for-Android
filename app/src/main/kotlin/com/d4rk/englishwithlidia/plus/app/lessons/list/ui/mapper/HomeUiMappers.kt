package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state.HomeLessonUiModel
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state.HomeUiState
import kotlinx.collections.immutable.toImmutableList
internal fun HomeScreen.toUiState(): HomeUiState =
    HomeUiState(lessons = lessons.map(HomeLesson::toUiModel).toImmutableList())

internal fun HomeLesson.toUiModel(): HomeLessonUiModel =
    HomeLessonUiModel(
        lessonId = lessonId,
        lessonTitle = lessonTitle,
        lessonType = lessonType,
        lessonThumbnailImageUrl = lessonThumbnailImageUrl,
        lessonDeepLinkPath = lessonDeepLinkPath,
    )
