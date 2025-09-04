package com.d4rk.englishwithlidia.plus.playback

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class AudioPlaybackService : MediaSessionService() {

    private val player : ExoPlayer by lazy {
        val audioAttributes : AudioAttributes = AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_SPEECH).setUsage(C.USAGE_MEDIA).build()
        ExoPlayer.Builder(this).setAudioAttributes(audioAttributes , true).build()
    }

    private val mediaSession : MediaSession by lazy {
        MediaSession.Builder(this , player)
            .setCallback(MediaSessionCallback())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        mediaSession
    }

    override fun onGetSession(controllerInfo : MediaSession.ControllerInfo) : MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        player.release()
        mediaSession.release()
        super.onDestroy()
    }

    private inner class MediaSessionCallback : MediaSession.Callback {
        override fun onAddMediaItems(mediaSession : MediaSession , controller : MediaSession.ControllerInfo , mediaItems : MutableList<MediaItem>) : ListenableFuture<MutableList<MediaItem>> {
            if (mediaItems.isNotEmpty()) {
                player.clearMediaItems()
                val singleMediaItem : MediaItem = mediaItems.first()
                player.setMediaItem(singleMediaItem)
                return Futures.immediateFuture(mutableListOf(singleMediaItem))
            }
            return Futures.immediateFuture(mutableListOf())
        }
    }
}
