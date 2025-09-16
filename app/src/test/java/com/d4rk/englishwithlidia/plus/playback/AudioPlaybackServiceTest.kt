package com.d4rk.englishwithlidia.plus.playback

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.google.common.truth.Truth.assertThat
import io.mockk.any
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AudioPlaybackServiceTest {

    private lateinit var service: AudioPlaybackService
    private lateinit var callback: MediaSession.Callback
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSession

    @BeforeEach
    fun setUp() {
        exoPlayer = mockk(relaxed = true)
        mediaSession = mockk(relaxed = true)

        service = AudioPlaybackService()
        overrideLazyDelegate("player", exoPlayer)
        overrideLazyDelegate("mediaSession", mediaSession)

        callback = createCallback()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `onAddMediaItems with multiple items returns only first and clears previous`() {
        val firstItem = MediaItem.Builder().setMediaId("first").build()
        val secondItem = MediaItem.Builder().setMediaId("second").build()
        val controllerInfo = mockk<MediaSession.ControllerInfo>(relaxed = true)

        val result = callback.onAddMediaItems(
            mediaSession,
            controllerInfo,
            mutableListOf(firstItem, secondItem)
        ).get()

        verifyOrder {
            exoPlayer.clearMediaItems()
            exoPlayer.setMediaItem(firstItem)
        }
        verify(exactly = 1) { exoPlayer.clearMediaItems() }
        verify(exactly = 1) { exoPlayer.setMediaItem(firstItem) }
        assertThat(result).containsExactly(firstItem)
    }

    @Test
    fun `onAddMediaItems with empty list returns empty result`() {
        val controllerInfo = mockk<MediaSession.ControllerInfo>(relaxed = true)

        val result = callback.onAddMediaItems(
            mediaSession,
            controllerInfo,
            mutableListOf()
        ).get()

        verify(exactly = 0) { exoPlayer.clearMediaItems() }
        verify(exactly = 0) { exoPlayer.setMediaItem(any()) }
        assertThat(result).isEmpty()
    }

    private fun overrideLazyDelegate(propertyName: String, value: Any) {
        val delegateField = AudioPlaybackService::class.java.getDeclaredField("${propertyName}\$delegate")
        delegateField.isAccessible = true
        delegateField.set(service, lazyOf(value))
    }

    private fun createCallback(): MediaSession.Callback {
        val callbackClassName = "${AudioPlaybackService::class.qualifiedName}\$MediaSessionCallback"
        val callbackClass = Class.forName(callbackClassName)
        val constructor = callbackClass.getDeclaredConstructor(AudioPlaybackService::class.java)
        constructor.isAccessible = true
        return constructor.newInstance(service) as MediaSession.Callback
    }
}
