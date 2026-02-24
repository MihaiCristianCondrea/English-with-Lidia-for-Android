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

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state

import androidx.compose.runtime.Immutable

@Immutable
data class UiLessonScreen(
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val playbackPosition: Long = 0L,
    val playbackDuration: Long = 0L,
    val hasPlaybackError: Boolean = false,
    val lessonTitle: String = "",
    val writer: String = "",
    val lessonContent: List<UiLessonContent> = emptyList(),
)

@Immutable
data class UiLessonContent(
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
