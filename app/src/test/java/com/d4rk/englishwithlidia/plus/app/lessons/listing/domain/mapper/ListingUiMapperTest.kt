/*
package com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingLesson
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ListingUiMapperTest {

    @Test
    fun `map maps domain lessons to ui lessons correctly`() {
        val domainLessons = listOf(
            ListingLesson(
                lessonId = "1",
                lessonTitle = "First lesson",
                lessonType = "video",
                lessonThumbnailImageUrl = "url1",
                lessonDeepLinkPath = "path1",
            ),
            ListingLesson(
                lessonId = "2",
                lessonTitle = "Second lesson",
                lessonType = "audio",
                lessonThumbnailImageUrl = "url2",
                lessonDeepLinkPath = "path2",
            ),
        )
        val domainScreen = ListingScreen(lessons = domainLessons)

        val uiScreen = ListingUiMapper().map(domainScreen)

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

*/
