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
