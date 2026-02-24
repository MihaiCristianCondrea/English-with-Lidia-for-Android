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

package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model

import androidx.compose.runtime.Immutable

/**
 * Represents the business data for a lesson before it is adapted for the UI layer.
 */
@Immutable
data class Lesson(
    val lessonTitle: String = "",
    val writer: String = "",
    val lessonContent: List<LessonContent> = emptyList(),
)

/**
 * Represents the raw lesson content returned by the backend service.
 */
@Immutable
data class LessonContent(
    val contentId: String = "",
    val contentType: String = "",
    val contentText: String = "",
    val contentImageUrl: String = "",
    val contentAudioUrl: String = "",
    val contentThumbnailUrl: String = "",
    val contentTitle: String = "",
    val contentArtist: String = "",
    val contentAlbumTitle: String = "",
    val contentGenre: String = "",
    val contentDescription: String = "",
    val contentReleaseYear: Int? = null,
    val writer: String = "",
)
