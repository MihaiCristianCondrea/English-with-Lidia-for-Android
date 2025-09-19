package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonConstants
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LessonListLayoutKtTest {

    @Test
    fun `buildAppListItems maps lesson types to list items`() {
        val lessons = listOf(
            UiHomeLesson(lessonType = LessonConstants.TYPE_BANNER_IMAGE_LOCAL),
            UiHomeLesson(lessonType = LessonConstants.TYPE_ROW_BUTTONS_LOCAL),
            UiHomeLesson(lessonType = LessonConstants.TYPE_AD_VIEW_BANNER),
            UiHomeLesson(lessonType = LessonConstants.TYPE_AD_VIEW_BANNER_LARGE),
            UiHomeLesson(
                lessonId = "id-1",
                lessonTitle = "Lesson title",
                lessonType = LessonConstants.TYPE_FULL_IMAGE_BANNER,
                lessonThumbnailImageUrl = "https://example.com/thumb.jpg",
                lessonDeepLinkPath = "/lesson/id-1",
            ),
            UiHomeLesson(
                lessonId = "id-2",
                lessonTitle = "Other title",
                lessonType = "unknown",
                lessonThumbnailImageUrl = "https://example.com/thumb2.jpg",
                lessonDeepLinkPath = "/lesson/id-2",
            ),
        )

        val items = buildAppListItems(lessons)

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
