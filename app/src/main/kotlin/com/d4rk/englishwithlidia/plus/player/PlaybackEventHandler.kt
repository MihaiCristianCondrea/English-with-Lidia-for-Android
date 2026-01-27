package com.d4rk.englishwithlidia.plus.player

interface PlaybackEventHandler {
    fun updateIsPlaying(isPlaying: Boolean)
    fun updateIsBuffering(isBuffering: Boolean)
    fun updatePlaybackDuration(duration: Long)
    fun updatePlaybackPosition(position: Long)
    fun onPlaybackError()
}
