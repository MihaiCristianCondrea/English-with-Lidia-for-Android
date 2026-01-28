package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingLesson
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingLessonUiModel
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingUiState
import kotlinx.collections.immutable.toImmutableList
internal fun ListingScreen.toUiState(): ListingUiState =
    ListingUiState(lessons = lessons.map(ListingLesson::toUiModel).toImmutableList())

internal fun ListingLesson.toUiModel(): ListingLessonUiModel =
    ListingLessonUiModel(
        lessonId = lessonId,
        lessonTitle = lessonTitle,
        lessonType = lessonType,
        lessonThumbnailImageUrl = lessonThumbnailImageUrl,
        lessonDeepLinkPath = lessonDeepLinkPath,
    )
