package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model

import androidx.compose.runtime.Immutable

/**
 * Represents the business data for a lesson before it is adapted for the UI layer.
 */
@Immutable
data class Lesson(
    val lessonTitle: String = "",
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
)
