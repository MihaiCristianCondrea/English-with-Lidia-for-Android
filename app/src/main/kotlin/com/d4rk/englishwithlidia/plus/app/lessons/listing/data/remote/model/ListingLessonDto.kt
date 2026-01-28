package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingLessonDto(
    @SerialName("lesson_id") val lessonId: String = "",
    @SerialName("lesson_title") val lessonTitle: String = "",
    @SerialName("lesson_type") val lessonType: String = "",
    @SerialName("lesson_thumbnail_image_url") val lessonThumbnailImageUrl: String = "",
    @SerialName("lesson_deep_link_path") val lessonDeepLinkPath: String = "",
)
