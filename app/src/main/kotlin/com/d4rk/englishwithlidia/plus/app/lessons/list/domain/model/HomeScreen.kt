package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model

/**
 * Domain representation of the home lessons screen.
 */
data class HomeScreen(
    val lessons: List<HomeLesson> = emptyList(),
)

/**
 * Domain representation of a single lesson item.
 */
data class HomeLesson(
    val lessonId: String = "",
    val lessonTitle: String = "",
    val lessonType: String = "",
    val lessonThumbnailImageUrl: String = "",
    val lessonDeepLinkPath: String = "",
)
