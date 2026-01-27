package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeUiState(
    val lessons: ImmutableList<HomeLessonUiModel> = persistentListOf(),
)

@Immutable
data class HomeLessonUiModel(
    val lessonId: String = "",
    val lessonTitle: String = "",
    val lessonType: String = "",
    val lessonThumbnailImageUrl: String = "",
    val lessonDeepLinkPath: String = "",
)
