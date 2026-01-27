package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonScreen

class LessonUiMapper {

    fun map(lesson: Lesson): UiLessonScreen {
        return UiLessonScreen(
            lessonTitle = lesson.lessonTitle,
            lessonContent = lesson.lessonContent.map(::mapContent),
        )
    }

    private fun mapContent(content: LessonContent): UiLessonContent {
        return UiLessonContent(
            contentId = content.contentId,
            contentType = content.contentType,
            contentText = content.contentText,
            contentImageUrl = content.contentImageUrl,
            contentAudioUrl = content.contentAudioUrl,
            contentThumbnailUrl = content.contentThumbnailUrl,
            contentTitle = content.contentTitle,
            contentArtist = content.contentArtist,
            contentAlbumTitle = content.contentAlbumTitle,
            contentGenre = content.contentGenre,
            contentDescription = content.contentDescription,
            contentReleaseYear = content.contentReleaseYear,
        )
    }
}
