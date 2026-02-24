/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
