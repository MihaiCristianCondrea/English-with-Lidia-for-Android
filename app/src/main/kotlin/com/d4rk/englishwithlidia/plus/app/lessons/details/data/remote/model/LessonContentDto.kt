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

package com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonContentDto(
    @SerialName("content_id") val contentId: String = "",
    @SerialName("content_type") val contentType: String = "",
    @SerialName("content_text") val contentText: String = "",
    @SerialName("content_audio_url") val contentAudioUrl: String = "",
    @SerialName("content_image_url") val contentImageUrl: String = "",
    @SerialName("content_thumbnail_url") val contentThumbnailUrl: String = "",
    @SerialName("content_title") val contentTitle: String = "",
    @SerialName("content_artist") val contentArtist: String = "",
    @SerialName("content_album_title") val contentAlbumTitle: String = "",
    @SerialName("content_genre") val contentGenre: String = "",
    @SerialName("content_description") val contentDescription: String = "",
    @SerialName("content_release_year") val contentReleaseYear: Int? = null,
    @SerialName("writer") val writer: String = "",
)
