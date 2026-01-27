package com.d4rk.englishwithlidia.plus.app.lessons.list.data.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.model.HomeLessonDto
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.model.HomeLessonsResponseDto
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen

internal fun HomeLessonsResponseDto.toDomain(): HomeScreen =
    HomeScreen(lessons = data.mapNotNull(HomeLessonDto::toDomainOrNull))

internal fun HomeLessonDto.toDomainOrNull(): HomeLesson? {
    if (lessonId.isBlank()) return null

    return HomeLesson(
        lessonId = lessonId,
        lessonTitle = lessonTitle,
        lessonType = lessonType,
        lessonThumbnailImageUrl = lessonThumbnailImageUrl,
        lessonDeepLinkPath = lessonDeepLinkPath,
    )
}
