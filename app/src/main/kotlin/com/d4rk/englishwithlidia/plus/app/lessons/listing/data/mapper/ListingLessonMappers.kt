package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model.ListingLessonDto
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model.ListingLessonsResponseDto
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingLesson
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen

internal fun ListingLessonsResponseDto.toDomain(): ListingScreen =
    ListingScreen(lessons = data.mapNotNull(ListingLessonDto::toDomainOrNull))

internal fun ListingLessonDto.toDomainOrNull(): ListingLesson? {
    if (lessonId.isBlank()) return null

    return ListingLesson(
        lessonId = lessonId,
        lessonTitle = lessonTitle,
        lessonType = lessonType,
        lessonThumbnailImageUrl = lessonThumbnailImageUrl,
        lessonDeepLinkPath = lessonDeepLinkPath,
    )
}
