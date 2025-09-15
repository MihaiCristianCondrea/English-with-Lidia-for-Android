package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HomeUiMapperTest {

    @Test
    fun `map maps domain lessons to ui lessons correctly`() {
        val domainLessons = listOf(
            HomeLesson(
                lessonId = "1",
                lessonTitle = "First lesson",
                lessonType = "video",
                lessonThumbnailImageUrl = "url1",
                lessonDeepLinkPath = "path1",
            ),
            HomeLesson(
                lessonId = "2",
                lessonTitle = "Second lesson",
                lessonType = "audio",
                lessonThumbnailImageUrl = "url2",
                lessonDeepLinkPath = "path2",
            ),
        )
        val domainScreen = HomeScreen(lessons = domainLessons)

        val uiScreen = HomeUiMapper().map(domainScreen)

        assertEquals(domainLessons.size, uiScreen.lessons.size)
        domainLessons.zip(uiScreen.lessons).forEach { (domain, ui) ->
            assertEquals(domain.lessonId, ui.lessonId)
            assertEquals(domain.lessonTitle, ui.lessonTitle)
            assertEquals(domain.lessonType, ui.lessonType)
            assertEquals(domain.lessonThumbnailImageUrl, ui.lessonThumbnailImageUrl)
            assertEquals(domain.lessonDeepLinkPath, ui.lessonDeepLinkPath)
        }
    }
}

