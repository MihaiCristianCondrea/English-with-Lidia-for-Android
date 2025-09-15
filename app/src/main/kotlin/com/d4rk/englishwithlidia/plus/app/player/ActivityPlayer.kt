package com.d4rk.englishwithlidia.plus.app.player

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.PlaybackException
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.d4rk.englishwithlidia.plus.core.utils.extensions.await
import com.d4rk.englishwithlidia.plus.playback.AudioPlaybackService
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ActivityPlayer : AppCompatActivity() {

    protected abstract val playbackHandler: PlaybackEventHandler

    private var controllerFuture: ListenableFuture<MediaController>? = null
    protected var player: Player? = null
    private var positionJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        val intent = Intent(context, AudioPlaybackService::class.java)
        context.startService(intent)
        val sessionToken =
            SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        lifecycleScope.launch {
            player = controllerFuture?.await()
            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    playbackHandler.updateIsPlaying(isPlaying)
                    if (isPlaying) {
                        startPositionUpdates()
                    } else {
                        positionJob?.cancel()
                    }
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        val duration = player?.duration ?: 0L
                        playbackHandler.updatePlaybackDuration(duration)
                    }
                }
                override fun onPlayerError(error: PlaybackException) {
                    playbackHandler.updateIsPlaying(false)
                    playbackHandler.onPlaybackError()
                    positionJob?.cancel()
                }
            })
        }
    }

    fun preparePlayer(
        audioUrl: String,
        title: String,
        thumbnailUrl: String? = null,
        artist: String? = null,
        albumTitle: String? = null,
        genre: String? = null,
        description: String? = null,
        releaseYear: Int? = null
    ) {
        lifecycleScope.launch {
            controllerFuture?.await()?.let { controller ->
                val metadataBuilder = MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtworkUri(thumbnailUrl?.toUri())
                    .setArtist(artist)
                    .setAlbumTitle(albumTitle)
                    .setGenre(genre)
                    .setDescription(description)
                    .setReleaseYear(releaseYear)

                val mediaItem = MediaItem.Builder()
                    .setUri(audioUrl.toUri())
                    .setMediaMetadata(metadataBuilder.build())
                    .build()

                controller.setMediaItem(mediaItem)
                controller.prepare()
                controller.playWhenReady = false
            }
        }
    }

    fun playPause() {
        player?.let { controller ->
            if (controller.isPlaying) {
                controller.pause()
            } else {
                controller.play()
            }
        }
    }

    private fun startPositionUpdates() {
        positionJob?.cancel()
        positionJob = lifecycleScope.launch(Dispatchers.Default) {
            while (true) {
                withContext(Dispatchers.Main) {
                    val currentPosition = player?.currentPosition ?: 0L
                    playbackHandler.updatePlaybackPosition(currentPosition)

                    if (player?.isPlaying != true) {
                        positionJob?.cancel()
                        return@withContext
                    }
                }
                if (positionJob?.isCancelled == true) break
                delay(500)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        positionJob?.cancel()
        controllerFuture?.let { MediaController.releaseFuture(it) }
    }

}
