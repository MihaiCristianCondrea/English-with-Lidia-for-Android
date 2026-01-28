package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.mapping

import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.components.LessonListItem
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingLessonUiModel
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonConstants
import kotlinx.collections.immutable.ImmutableList

internal fun listingListItemContentType(item: LessonListItem): String = when (item) {
    LessonListItem.BannerImage -> "banner_image"
    LessonListItem.ActionButtons -> "action_buttons"
    LessonListItem.BannerAd -> "banner_ad"
    LessonListItem.MediumRectangleAd -> "medium_rectangle_ad"
    is LessonListItem.Lesson -> "lesson"
}

internal fun buildListingListItems(
    lessons: ImmutableList<ListingLessonUiModel>,
): List<LessonListItem> = buildList {
    lessons.forEach { lesson ->
        when (lesson.lessonType) {
            LessonConstants.TYPE_BANNER_IMAGE_LOCAL -> add(LessonListItem.BannerImage)
            LessonConstants.TYPE_ROW_BUTTONS_LOCAL -> add(LessonListItem.ActionButtons)
            LessonConstants.TYPE_AD_VIEW_BANNER -> add(LessonListItem.BannerAd)
            LessonConstants.TYPE_AD_VIEW_BANNER_LARGE -> add(LessonListItem.MediumRectangleAd)
            else -> add(LessonListItem.Lesson(lesson))
        }
    }
}
