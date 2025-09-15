package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui

import androidx.compose.runtime.Immutable

@Immutable
data class UiLessonScreen(
    val isPlaying: Boolean = false,
    val playbackPosition: Long = 0L,
    val playbackDuration: Long = 0L,
    val hasPlaybackError: Boolean = false,
    val lessonTitle: String = "",
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
)
