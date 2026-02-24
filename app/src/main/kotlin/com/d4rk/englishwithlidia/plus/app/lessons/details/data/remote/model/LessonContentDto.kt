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
