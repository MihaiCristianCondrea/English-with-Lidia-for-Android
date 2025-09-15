package com.d4rk.englishwithlidia.plus.app.player

interface PlaybackEventHandler {
    fun updateIsPlaying(isPlaying: Boolean)
    fun updatePlaybackDuration(duration: Long)
    fun updatePlaybackPosition(position: Long)
    fun onPlaybackError()
}
