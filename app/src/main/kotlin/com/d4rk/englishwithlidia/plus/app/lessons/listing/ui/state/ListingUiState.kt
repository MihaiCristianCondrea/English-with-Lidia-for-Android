package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ListingUiState(
    val lessons: ImmutableList<ListingLessonUiModel> = persistentListOf(),
)

@Immutable
data class ListingLessonUiModel(
    val lessonId: String = "",
    val lessonTitle: String = "",
    val lessonType: String = "",
    val lessonThumbnailImageUrl: String = "",
    val lessonDeepLinkPath: String = "",
)
