package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen

/**
 * Converts domain models into UI models for the home lessons screen.
 */
class HomeUiMapper {
    fun map(screen: HomeScreen): UiHomeScreen =
        UiHomeScreen(lessons = screen.lessons.map(::mapLesson))

    private fun mapLesson(lesson: HomeLesson): UiHomeLesson =
        UiHomeLesson(
            lessonId = lesson.lessonId,
            lessonTitle = lesson.lessonTitle,
            lessonType = lesson.lessonType,
            lessonThumbnailImageUrl = lesson.lessonThumbnailImageUrl,
            lessonDeepLinkPath = lesson.lessonDeepLinkPath,
        )
}

