package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse

class LessonMapper {

    fun map(response: ApiLessonResponse): List<Lesson> {
        return response.data.map { networkLesson ->
            Lesson(
                lessonTitle = networkLesson.lessonTitle,
                lessonContent = networkLesson.lessonContent.map { networkContent ->
                    LessonContent(
                        contentId = networkContent.contentId,
                        contentType = networkContent.contentType,
                        contentText = networkContent.contentText,
                        contentAudioUrl = networkContent.contentAudioUrl,
                        contentImageUrl = networkContent.contentImageUrl,
                        contentThumbnailUrl = networkContent.contentThumbnailUrl,
                        contentTitle = networkContent.contentTitle,
                        contentArtist = networkContent.contentArtist,
                        contentAlbumTitle = networkContent.contentAlbumTitle,
                        contentGenre = networkContent.contentGenre,
                        contentDescription = networkContent.contentDescription,
                        contentReleaseYear = networkContent.contentReleaseYear,
                    )
                },
            )
        }
    }
}

