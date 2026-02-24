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

package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.mapping

import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views.LessonListItem
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
