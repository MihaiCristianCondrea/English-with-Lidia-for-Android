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
import kotlinx.coroutines.CancellationException
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

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            playbackHandler.updateIsPlaying(isPlaying)
            if (isPlaying) {
                startPositionUpdates()
            } else {
                positionJob?.cancel()
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    val shouldShowBuffering = player?.playWhenReady == true
                    playbackHandler.updateIsBuffering(shouldShowBuffering)
                }

                Player.STATE_READY -> {
                    playbackHandler.updateIsBuffering(false)
                    val duration = player?.duration ?: 0L
                    playbackHandler.updatePlaybackDuration(duration)
                }

                Player.STATE_IDLE, Player.STATE_ENDED -> {
                    playbackHandler.updateIsBuffering(false)
                }
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            playbackHandler.updateIsPlaying(false)
            playbackHandler.updateIsBuffering(false)
            playbackHandler.onPlaybackError()
            positionJob?.cancel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        val intent = Intent(context, AudioPlaybackService::class.java)
        context.startService(intent)
    }

    override fun onStart() {
        super.onStart()
        if (controllerFuture != null) return

        val context = applicationContext
        val sessionToken = SessionToken(context, ComponentName(context, AudioPlaybackService::class.java))
        val future = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture = future
        lifecycleScope.launch {
            try {
                val controller = future.await()
                if (controllerFuture != future) {
                    return@launch
                }
                player = controller
                controller.addListener(playerListener)
                playbackHandler.updateIsPlaying(controller.isPlaying)
                val isBuffering =
                    controller.playbackState == Player.STATE_BUFFERING && controller.playWhenReady
                playbackHandler.updateIsBuffering(isBuffering)
                if (controller.playbackState == Player.STATE_READY) {
                    playbackHandler.updatePlaybackDuration(controller.duration)
                }
                if (controller.isPlaying) {
                    startPositionUpdates()
                }
            } catch (_: CancellationException) {
                // Ignored. The controller future was cancelled, typically because the Activity stopped.
            } catch (_: Exception) {
                controllerFuture = null
                player = null
                MediaController.releaseFuture(future)
            }
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
            val controller = player ?: controllerFuture?.await()
            controller?.let {
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

                it.setMediaItem(mediaItem)
                it.prepare()
                it.playWhenReady = false
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

    fun seekTo(positionMillis: Long) {
        player?.seekTo(positionMillis)
        playbackHandler.updatePlaybackPosition(positionMillis)
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

    override fun onStop() {
        super.onStop()
        positionJob?.cancel()
        player?.let { controller ->
            if (controller.isPlaying) {
                controller.pause()
            }
            controller.stop()
            controller.clearMediaItems()
            controller.removeListener(playerListener)
        }
        player = null
        controllerFuture?.let { MediaController.releaseFuture(it) }
        controllerFuture = null
    }

    override fun onDestroy() {
        super.onDestroy()
        positionJob?.cancel()
    }
}
