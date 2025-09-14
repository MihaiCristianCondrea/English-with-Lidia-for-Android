package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.d4rk.android.libs.apptoolkit.app.theme.style.AppTheme
import com.d4rk.englishwithlidia.plus.app.player.ActivityPlayer
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonContentTypes
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonActivity : ActivityPlayer() {
    override val playbackHandler: LessonViewModel by viewModel()
    private val viewModel: LessonViewModel
        get() = playbackHandler
    private var isPlayerPrepared = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val lessonId = intent?.data?.lastPathSegment
        lessonId?.let { viewModel.getLesson(it) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (!isPlayerPrepared) {
                        state.data?.lessonContent?.firstOrNull { it.contentType == LessonContentTypes.CONTENT_PLAYER }?.let { content ->
                            preparePlayer(
                                audioUrl = content.contentAudioUrl,
                                title = content.contentTitle.ifBlank { state.data.lessonTitle },
                                thumbnailUrl = content.contentThumbnailUrl,
                                artist = content.contentArtist,
                                albumTitle = content.contentAlbumTitle,
                                genre = content.contentGenre,
                                description = content.contentDescription,
                                releaseYear = content.contentReleaseYear
                            )
                            isPlayerPrepared = true
                        }
                    }
                }
            }
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LessonScreen(
                        viewModel = viewModel,
                        onBack = { finish() },
                        onPlayClick = { playPause() },
                        onSeekChange = { newPosition ->
                            seekTo((newPosition * 1000).toLong())
                        },
                    )
                }
            }
        }
    }
}
