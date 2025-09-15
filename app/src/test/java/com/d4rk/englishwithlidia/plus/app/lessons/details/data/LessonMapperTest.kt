package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLesson
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonContent
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LessonMapperTest {

    @Test
    fun `map maps api response to ui lesson screens correctly`() {
        val apiResponse = ApiLessonResponse(
            data = listOf(
                ApiLesson(
                    lessonTitle = "Lesson 1",
                    lessonContent = listOf(
                        ApiLessonContent(
                            contentId = "content1",
                            contentType = "type1",
                            contentText = "text1",
                            contentAudioUrl = "audio1",
                            contentImageUrl = "image1",
                            contentThumbnailUrl = "thumb1",
                            contentTitle = "title1",
                            contentArtist = "artist1",
                            contentAlbumTitle = "album1",
                            contentGenre = "genre1",
                            contentDescription = "description1",
                            contentReleaseYear = 2001,
                        ),
                        ApiLessonContent(
                            contentId = "content2",
                            contentType = "type2",
                            contentText = "text2",
                            contentAudioUrl = "audio2",
                            contentImageUrl = "image2",
                            contentThumbnailUrl = "thumb2",
                            contentTitle = "title2",
                            contentArtist = "artist2",
                            contentAlbumTitle = "album2",
                            contentGenre = "genre2",
                            contentDescription = "description2",
                            contentReleaseYear = 2002,
                        ),
                    ),
                ),
            ),
        )

        val uiLessons = LessonMapper().map(apiResponse)

        assertEquals(1, uiLessons.size)
        val uiLesson = uiLessons[0]
        assertEquals("Lesson 1", uiLesson.lessonTitle)
        assertEquals(2, uiLesson.lessonContent.size)

        val expectedContents = apiResponse.data[0].lessonContent
        uiLesson.lessonContent.zip(expectedContents).forEach { (ui, api) ->
            assertEquals(api.contentId, ui.contentId)
            assertEquals(api.contentType, ui.contentType)
            assertEquals(api.contentText, ui.contentText)
            assertEquals(api.contentAudioUrl, ui.contentAudioUrl)
            assertEquals(api.contentImageUrl, ui.contentImageUrl)
            assertEquals(api.contentThumbnailUrl, ui.contentThumbnailUrl)
            assertEquals(api.contentTitle, ui.contentTitle)
            assertEquals(api.contentArtist, ui.contentArtist)
            assertEquals(api.contentAlbumTitle, ui.contentAlbumTitle)
            assertEquals(api.contentGenre, ui.contentGenre)
            assertEquals(api.contentDescription, ui.contentDescription)
            assertEquals(api.contentReleaseYear, ui.contentReleaseYear)
        }
    }

    @Test
    fun `map returns empty list when response data is empty`() {
        val apiResponse = ApiLessonResponse(data = emptyList())

        val uiLessons = LessonMapper().map(apiResponse)

        assertEquals(0, uiLessons.size)
    }
}

