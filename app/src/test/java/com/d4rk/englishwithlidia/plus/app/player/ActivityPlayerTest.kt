package com.d4rk.englishwithlidia.plus.app.player

import android.os.Looper
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.Futures
import io.mockk.anyConstructed
import io.mockk.every
import io.mockk.firstArg
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.junit5.RobolectricExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(RobolectricExtension::class)
class ActivityPlayerTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var playbackHandler: PlaybackEventHandler
    private lateinit var fakePlayer: MediaController
    private lateinit var playerListener: Player.Listener
    private lateinit var activityController: ActivityController<TestActivityPlayer>

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        playbackHandler = mockk(relaxed = true)
        fakePlayer = mockk(relaxed = true)

        mockkConstructor(MediaController.Builder::class)
        every { anyConstructed<MediaController.Builder>().buildAsync() } returns
            Futures.immediateFuture(fakePlayer)
        every { fakePlayer.addListener(any()) } answers {
            playerListener = firstArg()
            Unit
        }

        activityController = Robolectric.buildActivity(TestActivityPlayer::class.java)
        val activity = activityController.get()
        activity.setPlaybackHandler(playbackHandler)
        activityController.setup()

        Shadows.shadowOf(Looper.getMainLooper()).idle()

        assertThat(::playerListener.isInitialized).isTrue()
    }

    @AfterEach
    fun tearDown() {
        if (::activityController.isInitialized) {
            activityController.pause().destroy()
        }
        unmockkConstructor(MediaController.Builder::class)
        Dispatchers.resetMain()
    }

    @Test
    fun onIsPlayingChanged_trueUpdatesStateAndPosition() {
        val expectedPosition = 1_500L
        every { fakePlayer.currentPosition } returns expectedPosition
        every { fakePlayer.isPlaying } returns false

        playerListener.onIsPlayingChanged(true)

        verify(exactly = 1) { playbackHandler.updateIsPlaying(true) }
        verify(timeout = 1_000, exactly = 1) {
            playbackHandler.updatePlaybackPosition(expectedPosition)
        }
    }

    @Test
    fun onIsPlayingChanged_falseUpdatesState() {
        playerListener.onIsPlayingChanged(false)

        verify(exactly = 1) { playbackHandler.updateIsPlaying(false) }
    }

    @Test
    fun onPlaybackStateReady_updatesDuration() {
        val expectedDuration = 12_345L
        every { fakePlayer.duration } returns expectedDuration

        playerListener.onPlaybackStateChanged(Player.STATE_READY)

        verify(exactly = 1) { playbackHandler.updatePlaybackDuration(expectedDuration) }
    }

    @Test
    fun onPlayerError_notifiesHandler() {
        val playbackException = PlaybackException(
            "test error",
            null,
            PlaybackException.ERROR_CODE_UNKNOWN
        )

        playerListener.onPlayerError(playbackException)

        verify(exactly = 1) { playbackHandler.updateIsPlaying(false) }
        verify(exactly = 1) { playbackHandler.onPlaybackError() }
    }

    private class TestActivityPlayer : ActivityPlayer() {

        private var handler: PlaybackEventHandler? = null

        override val playbackHandler: PlaybackEventHandler
            get() = handler ?: error("Playback handler not set")

        fun setPlaybackHandler(playbackHandler: PlaybackEventHandler) {
            handler = playbackHandler
        }
    }
}
