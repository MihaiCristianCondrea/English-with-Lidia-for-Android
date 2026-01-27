package com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonResponseDto(
    @SerialName("data") val data: List<LessonDto> = emptyList(),
)
