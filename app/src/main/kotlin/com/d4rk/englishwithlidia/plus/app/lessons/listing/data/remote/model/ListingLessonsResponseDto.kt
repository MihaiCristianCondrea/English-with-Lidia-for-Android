package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingLessonsResponseDto(
    @SerialName("data") val data: List<ListingLessonDto> = emptyList(),
)
