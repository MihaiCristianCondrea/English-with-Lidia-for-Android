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

package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ListingUiState(
    val lessons: ImmutableList<ListingLessonUiModel> = persistentListOf(),
)

@Immutable
data class ListingLessonUiModel(
    val lessonId: String = "",
    val lessonTitle: String = "",
    val lessonType: String = "",
    val lessonThumbnailImageUrl: String = "",
    val lessonDeepLinkPath: String = "",
    val writer: String = "",
)
