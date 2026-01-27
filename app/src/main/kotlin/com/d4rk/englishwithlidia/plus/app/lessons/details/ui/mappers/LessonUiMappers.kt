package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.mappers

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonScreen

// TODO: This could be moved to domain to bring the data to the screen already mapped
internal fun Lesson.toUiModel(): UiLessonScreen =
    UiLessonScreen(
        lessonTitle = lessonTitle,
        lessonContent = lessonContent.map(LessonContent::toUiModel),
    )

internal fun LessonContent.toUiModel(): UiLessonContent =
    UiLessonContent(
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
