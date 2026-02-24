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
package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.components

import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingLessonUiModel
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonConstants
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LessonListLayoutKtTest {

    @Test
    fun `buildListingListItems maps lesson types to list items`() {
        val lessons = listOf(
            ListingLessonUiModel(lessonType = LessonConstants.TYPE_BANNER_IMAGE_LOCAL),
            ListingLessonUiModel(lessonType = LessonConstants.TYPE_ROW_BUTTONS_LOCAL),
            ListingLessonUiModel(lessonType = LessonConstants.TYPE_AD_VIEW_BANNER),
            ListingLessonUiModel(lessonType = LessonConstants.TYPE_AD_VIEW_BANNER_LARGE),
            ListingLessonUiModel(
                lessonId = "id-1",
                lessonTitle = "Lesson title",
                lessonType = LessonConstants.TYPE_FULL_IMAGE_BANNER,
                lessonThumbnailImageUrl = "https://example.com/thumb.jpg",
                lessonDeepLinkPath = "/lesson/id-1",
            ),
            ListingLessonUiModel(
                lessonId = "id-2",
                lessonTitle = "Other title",
                lessonType = "unknown",
                lessonThumbnailImageUrl = "https://example.com/thumb2.jpg",
                lessonDeepLinkPath = "/lesson/id-2",
            ),
        )

        val items = buildListingListItems(lessons)

        val expected = listOf(
            LessonListItem.BannerImage,
            LessonListItem.ActionButtons,
            LessonListItem.BannerAd,
            LessonListItem.MediumRectangleAd,
            LessonListItem.Lesson(lessons[4]),
            LessonListItem.Lesson(lessons[5]),
        )

        assertEquals(expected, items)
    }
}
*/
