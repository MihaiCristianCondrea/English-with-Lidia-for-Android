package com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeLessonsResponseDto(
    @SerialName("data") val data: List<HomeLessonDto> = emptyList(),
)
