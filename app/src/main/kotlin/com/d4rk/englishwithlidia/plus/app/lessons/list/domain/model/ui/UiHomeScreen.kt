package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui

data class UiHomeScreen(
    val lessons: List<UiHomeLesson> = emptyList(),
)

data class UiHomeLesson(
    val lessonId: String = "",
    val lessonTitle: String = "",
    val lessonType: String = "",
    val lessonThumbnailImageUrl: String = "",
    val lessonDeepLinkPath: String = "",
)
