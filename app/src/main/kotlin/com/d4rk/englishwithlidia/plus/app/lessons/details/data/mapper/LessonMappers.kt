package com.d4rk.englishwithlidia.plus.app.lessons.details.data.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonContentDto
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonDto
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonResponseDto
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent

internal fun LessonResponseDto.firstLessonOrNull(): Lesson? =
    data.firstOrNull()?.toDomain()

internal fun LessonDto.toDomain(): Lesson =
    Lesson(
        lessonTitle = lessonTitle,
        lessonContent = lessonContent.map(LessonContentDto::toDomain),
    )

internal fun LessonContentDto.toDomain(): LessonContent =
    LessonContent(
        contentId = contentId,
        contentType = contentType,
        contentText = contentText,
        contentImageUrl = contentImageUrl,
        contentAudioUrl = contentAudioUrl,
        contentThumbnailUrl = contentThumbnailUrl,
        contentTitle = contentTitle,
        contentArtist = contentArtist,
        contentAlbumTitle = contentAlbumTitle,
        contentGenre = contentGenre,
        contentDescription = contentDescription,
        contentReleaseYear = contentReleaseYear,
    )
