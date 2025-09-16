package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLesson
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonContent
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LessonMapperTest {

    private val mapper = LessonMapper()

    @Test
    fun `map maps api response with data to ui lessons`() {
        val apiResponse = createLessonApiResponse()

        val result = mapper.map(apiResponse)

        assertEquals(listOf(expectedUiLesson()), result)
    }

    @Test
    fun `map returns empty list when response has no data`() {
        val emptyApiResponse = createEmptyLessonApiResponse()

        val result = mapper.map(emptyApiResponse)

        assertTrue(result.isEmpty())
    }

    private fun createLessonApiResponse() = ApiLessonResponse(
        data = listOf(
            ApiLesson(
                lessonTitle = LESSON_TITLE,
                lessonContent = sampleContents.map { it.toApi() },
            ),
        ),
    )

    private fun createEmptyLessonApiResponse() = ApiLessonResponse(data = emptyList())

    private fun expectedUiLesson() = UiLessonScreen(
        lessonTitle = LESSON_TITLE,
        lessonContent = sampleContents.map { it.toUi() },
    )

    private data class LessonContentSample(
        val id: String,
        val type: String,
        val text: String,
        val audioUrl: String,
        val imageUrl: String,
        val thumbnailUrl: String,
        val title: String,
        val artist: String,
        val albumTitle: String,
        val genre: String,
        val description: String,
        val releaseYear: Int?,
    ) {
        fun toApi() = ApiLessonContent(
            contentId = id,
            contentType = type,
            contentText = text,
            contentAudioUrl = audioUrl,
            contentImageUrl = imageUrl,
            contentThumbnailUrl = thumbnailUrl,
            contentTitle = title,
            contentArtist = artist,
            contentAlbumTitle = albumTitle,
            contentGenre = genre,
            contentDescription = description,
            contentReleaseYear = releaseYear,
        )

        fun toUi() = UiLessonContent(
            contentId = id,
            contentType = type,
            contentText = text,
            contentAudioUrl = audioUrl,
            contentImageUrl = imageUrl,
            contentThumbnailUrl = thumbnailUrl,
            contentTitle = title,
            contentArtist = artist,
            contentAlbumTitle = albumTitle,
            contentGenre = genre,
            contentDescription = description,
            contentReleaseYear = releaseYear,
        )
    }

    private companion object {
        private const val LESSON_TITLE = "Lesson 1"

        private val sampleContents = listOf(
            LessonContentSample(
                id = "content1",
                type = "type1",
                text = "text1",
                audioUrl = "audio1",
                imageUrl = "image1",
                thumbnailUrl = "thumb1",
                title = "title1",
                artist = "artist1",
                albumTitle = "album1",
                genre = "genre1",
                description = "description1",
                releaseYear = 2001,
            ),
            LessonContentSample(
                id = "content2",
                type = "type2",
                text = "text2",
                audioUrl = "audio2",
                imageUrl = "image2",
                thumbnailUrl = "thumb2",
                title = "title2",
                artist = "artist2",
                albumTitle = "album2",
                genre = "genre2",
                description = "description2",
                releaseYear = 2002,
            ),
        )
    }
}
