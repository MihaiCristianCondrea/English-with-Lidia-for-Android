package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class LessonUiMapperTest {

    private val mapper = LessonUiMapper()

    @Test
    fun `map maps lesson to ui screen`() {
        val lesson = Lesson(
            lessonTitle = "Lesson title",
            lessonContent = listOf(
                LessonContent(
                    contentId = "1",
                    contentType = "type",
                    contentText = "text",
                    contentImageUrl = "image",
                    contentAudioUrl = "audio",
                    contentThumbnailUrl = "thumb",
                    contentTitle = "title",
                    contentArtist = "artist",
                    contentAlbumTitle = "album",
                    contentGenre = "genre",
                    contentDescription = "description",
                    contentReleaseYear = 2024,
                ),
            ),
        )

        val result = mapper.map(lesson)

        val expected = UiLessonScreen(
            lessonTitle = lesson.lessonTitle,
            lessonContent = lesson.lessonContent.map {
                UiLessonContent(
                    contentId = it.contentId,
                    contentType = it.contentType,
                    contentText = it.contentText,
                    contentImageUrl = it.contentImageUrl,
                    contentAudioUrl = it.contentAudioUrl,
                    contentThumbnailUrl = it.contentThumbnailUrl,
                    contentTitle = it.contentTitle,
                    contentArtist = it.contentArtist,
                    contentAlbumTitle = it.contentAlbumTitle,
                    contentGenre = it.contentGenre,
                    contentDescription = it.contentDescription,
                    contentReleaseYear = it.contentReleaseYear,
                )
            },
        )

        assertEquals(expected, result)
    }

    @Test
    fun `map handles empty content`() {
        val lesson = Lesson(lessonTitle = "Lesson", lessonContent = emptyList())

        val result = mapper.map(lesson)

        assertEquals(UiLessonScreen(lessonTitle = "Lesson"), result)
        assertNotNull(result.lessonContent)
        assertEquals(0, result.lessonContent.size)
    }
}
