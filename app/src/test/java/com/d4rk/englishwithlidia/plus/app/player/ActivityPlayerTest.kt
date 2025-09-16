package com.d4rk.englishwithlidia.plus.app.player

import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.SettableFuture
import io.mockk.Runs
import io.mockk.anyConstructed
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.junit5.RobolectricExtension

@ExtendWith(RobolectricExtension::class)
@Config(sdk = [34])
@LooperMode(LooperMode.Mode.PAUSED)
class ActivityPlayerTest {

    private lateinit var playbackHandler: PlaybackEventHandler
    private lateinit var mediaController: MediaController
    private lateinit var testScheduler: TestCoroutineScheduler
    private lateinit var testDispatcher: StandardTestDispatcher
    private lateinit var scopeJob: SupervisorJob
    private lateinit var testScope: TestScope
    private lateinit var lifecycleOwner: TestLifecycleOwner
    private lateinit var lifecycleScope: LifecycleCoroutineScope

    @BeforeEach
    fun setUp() {
        playbackHandler = mockk(relaxed = true)
        mediaController = mockk(relaxed = true)

        testScheduler = TestCoroutineScheduler()
        testDispatcher = StandardTestDispatcher(testScheduler)
        scopeJob = SupervisorJob()
        testScope = TestScope(testDispatcher + scopeJob)
        lifecycleOwner = TestLifecycleOwner()
        lifecycleScope = TestLifecycleCoroutineScope(lifecycleOwner.lifecycle, testScope)

        mockkObject(Dispatchers)
        every { Dispatchers.Main } returns testDispatcher
        every { Dispatchers.Main.immediate } returns testDispatcher
        every { Dispatchers.Default } returns testDispatcher

        mockkStatic("androidx.lifecycle.LifecycleOwnerKt")
        mockkStatic(MediaController::class)
        mockkConstructor(MediaController.Builder::class)

        every { MediaController.releaseFuture(any()) } just Runs
        every { mediaController.playWhenReady = any() } just Runs
        every { mediaController.prepare() } just Runs
        every { mediaController.play() } just Runs
        every { mediaController.pause() } just Runs
    }

    @AfterEach
    fun tearDown() {
        scopeJob.cancel()
        unmockkAll()
    }

    @Test
    fun preparePlayer_buildsMetadata_andPreparesController() {
        val context = createActivity()

        val mediaItemSlot = slot<MediaItem>()
        every { mediaController.setMediaItem(capture(mediaItemSlot)) } just Runs

        val audioUrl = "https://example.com/audio.mp3"
        val title = "Sample Title"
        val thumbnailUrl = "https://example.com/image.jpg"
        val artist = "Artist"
        val albumTitle = "Album"
        val genre = "Podcast"
        val description = "Episode description"
        val releaseYear = 2024

        context.activity.preparePlayer(
            audioUrl = audioUrl,
            title = title,
            thumbnailUrl = thumbnailUrl,
            artist = artist,
            albumTitle = albumTitle,
            genre = genre,
            description = description,
            releaseYear = releaseYear
        )
        testScheduler.runCurrent()

        val mediaItem = mediaItemSlot.captured
        assertEquals(title, mediaItem.mediaMetadata.title?.toString())
        assertEquals(Uri.parse(thumbnailUrl), mediaItem.mediaMetadata.artworkUri)
        assertEquals(artist, mediaItem.mediaMetadata.artist?.toString())
        assertEquals(albumTitle, mediaItem.mediaMetadata.albumTitle?.toString())
        assertEquals(genre, mediaItem.mediaMetadata.genre?.toString())
        assertEquals(description, mediaItem.mediaMetadata.description?.toString())
        assertEquals(releaseYear, mediaItem.mediaMetadata.releaseYear)
        assertEquals(Uri.parse(audioUrl), mediaItem.localConfiguration?.uri)

        verify(exactly = 1) { mediaController.setMediaItem(mediaItem) }
        verify(exactly = 1) { mediaController.prepare() }
        verify(exactly = 1) { mediaController.playWhenReady = false }
    }

    @Test
    fun playPause_togglesPlaybackAndNotifiesHandler() {
        val context = createActivity()
        var playing = false

        every { mediaController.isPlaying } answers { playing }
        every { mediaController.play() } answers {
            playing = true
            Unit
        }
        every { mediaController.pause() } answers {
            playing = false
            Unit
        }

        context.activity.playPause()
        verify(exactly = 1) { mediaController.play() }

        context.listener.onIsPlayingChanged(true)
        testScheduler.runCurrent()
        verify(exactly = 1) { playbackHandler.updateIsPlaying(true) }

        context.activity.playPause()
        verify(exactly = 1) { mediaController.pause() }

        context.listener.onIsPlayingChanged(false)
        testScheduler.runCurrent()
        verify(exactly = 1) { playbackHandler.updateIsPlaying(false) }
    }

    @Test
    fun positionUpdates_cancelJobWhenPlaybackStops() {
        val context = createActivity()
        var playing = true

        every { mediaController.isPlaying } answers { playing }
        every { mediaController.currentPosition } returnsMany listOf(1_000L, 2_000L)

        context.listener.onIsPlayingChanged(true)
        testScheduler.runCurrent()
        verify(exactly = 1) { playbackHandler.updatePlaybackPosition(1_000L) }

        playing = false
        context.listener.onIsPlayingChanged(false)
        testScheduler.runCurrent()
        testScheduler.advanceUntilIdle()

        val job = context.activity.positionJob()
        assertTrue(job == null || job.isCancelled)
        verify(exactly = 1) { playbackHandler.updatePlaybackPosition(any()) }
    }

    @Test
    fun onDestroy_cancelsPositionJobAndReleasesController() {
        val context = createActivity()
        var playing = true

        every { mediaController.isPlaying } answers { playing }
        every { mediaController.currentPosition } returns 3_000L

        context.listener.onIsPlayingChanged(true)
        testScheduler.runCurrent()

        val activeJob = context.activity.positionJob()
        assertNotNull(activeJob)
        assertTrue(activeJob!!.isActive)

        context.controller.destroy()
        testScheduler.runCurrent()

        val cancelledJob = context.activity.positionJob()
        assertTrue(cancelledJob == null || cancelledJob.isCancelled)
        verify(exactly = 1) { MediaController.releaseFuture(context.controllerFuture) }
    }

    private fun createActivity(): ActivityTestContext {
        val controllerFuture = SettableFuture.create<MediaController>()
        every { anyConstructed<MediaController.Builder>().buildAsync() } returns controllerFuture

        val controller = Robolectric.buildActivity(TestActivityPlayer::class.java)
        val activity = controller.get()
        activity.playbackHandlerDelegate = playbackHandler
        every { activity.lifecycleScope } returns lifecycleScope

        val listenerSlot = slot<Player.Listener>()
        every { mediaController.addListener(capture(listenerSlot)) } just Runs

        controller.setup()
        controllerFuture.set(mediaController)
        testScheduler.runCurrent()

        return ActivityTestContext(activity, listenerSlot.captured, controllerFuture, controller)
    }
}

private data class ActivityTestContext(
    val activity: TestActivityPlayer,
    val listener: Player.Listener,
    val controllerFuture: SettableFuture<MediaController>,
    val controller: ActivityController<TestActivityPlayer>
)

private class TestActivityPlayer : ActivityPlayer() {
    lateinit var playbackHandlerDelegate: PlaybackEventHandler

    override val playbackHandler: PlaybackEventHandler
        get() = playbackHandlerDelegate
}

private class TestLifecycleOwner : LifecycleOwner {
    private val registry = LifecycleRegistry(this).apply {
        handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        handleLifecycleEvent(Lifecycle.Event.ON_START)
        handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override val lifecycle: Lifecycle
        get() = registry
}

private class TestLifecycleCoroutineScope(
    override val lifecycle: Lifecycle,
    private val scope: CoroutineScope
) : LifecycleCoroutineScope() {
    override val coroutineContext = scope.coroutineContext
}

private fun ActivityPlayer.positionJob(): Job? {
    val field = ActivityPlayer::class.java.getDeclaredField("positionJob")
    field.isAccessible = true
    return field.get(this) as? Job
}
