package com.d4rk.englishwithlidia.plus.core.domain.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiHomeResponse(
    @SerialName("data") val data: List<ApiHomeLessons> = ArrayList()
)

@Serializable
data class ApiHomeLessons(
    @SerialName("lesson_id") val lessonId: String = "",
    @SerialName("lesson_title") val lessonTitle: String = "",
    @SerialName("lesson_type") val lessonType: String = "",
    @SerialName("lesson_thumbnail_image_url") var lessonThumbnailImageUrl: String = "",
    @SerialName("lesson_deep_link_path") val lessonDeepLinkPath: String = "",
)