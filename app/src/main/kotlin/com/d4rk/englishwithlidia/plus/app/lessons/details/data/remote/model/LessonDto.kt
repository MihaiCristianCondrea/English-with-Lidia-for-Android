package com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonDto(
    @SerialName("lesson_title") val lessonTitle: String = "",
    @SerialName("writer") val writer: String = "",
    @SerialName("lesson_content") val lessonContent: List<LessonContentDto> = emptyList(),
)
