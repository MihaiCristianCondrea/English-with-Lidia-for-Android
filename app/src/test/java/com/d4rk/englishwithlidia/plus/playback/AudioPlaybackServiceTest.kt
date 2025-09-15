
package com.d4rk.englishwithlidia.plus.playback

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import io.mockk.any
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.robolectric.Robolectric
import org.robolectric.junit5.RobolectricExtension

@ExtendWith(RobolectricExtension::class)
class AudioPlaybackServiceTest {

    private fun createServiceWithMockPlayer(player: ExoPlayer): MediaSession.Callback {
        val controller = Robolectric.buildService(AudioPlaybackService::class.java)
        val service = controller.get()

        val field = AudioPlaybackService::class.java.getDeclaredField("player\$delegate")
        field.isAccessible = true
        field.set(service, lazy { player })

        controller.create()

        val callbackClass = Class.forName("com.d4rk.englishwithlidia.plus.playback.AudioPlaybackService\$MediaSessionCallback")
        val ctor = callbackClass.getDeclaredConstructor(AudioPlaybackService::class.java)
        ctor.isAccessible = true
        return ctor.newInstance(service) as MediaSession.Callback
    }

    @Test
    fun `onAddMediaItems returns first item and updates player`() {
        val mockPlayer = mockk<ExoPlayer>(relaxed = true)
        val callback = createServiceWithMockPlayer(mockPlayer)

        val item1 = MediaItem.Builder().setMediaId("1").build()
        val item2 = MediaItem.Builder().setMediaId("2").build()
        val items = mutableListOf(item1, item2)

        val result = callback.onAddMediaItems(mockk<MediaSession>(), mockk<MediaSession.ControllerInfo>(), items).get()

        assertEquals(listOf(item1), result)
        verify(exactly = 1) { mockPlayer.clearMediaItems() }
        verify(exactly = 1) { mockPlayer.setMediaItem(item1) }
    }

    @Test
    fun `onAddMediaItems with empty list leaves player untouched`() {
        val mockPlayer = mockk<ExoPlayer>(relaxed = true)
        val callback = createServiceWithMockPlayer(mockPlayer)

        val result = callback.onAddMediaItems(mockk<MediaSession>(), mockk<MediaSession.ControllerInfo>(), mutableListOf()).get()

        assertTrue(result.isEmpty())
        verify(exactly = 0) { mockPlayer.clearMediaItems() }
        verify(exactly = 0) { mockPlayer.setMediaItem(any()) }
    }
}
