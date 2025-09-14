package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.d4rk.android.libs.apptoolkit.app.theme.style.AppTheme
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.englishwithlidia.plus.app.player.ActivityPlayer
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonContentTypes
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class LessonActivity : ActivityPlayer() {
    override val playbackHandler: LessonViewModel by viewModel()
    private val viewModel: LessonViewModel
        get() = playbackHandler
    private var isPlayerPrepared = false
    private val bannerConfig: AdsConfig by inject()
    private val mediumRectangleConfig: AdsConfig by inject(named("banner_medium_rectangle"))

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
                LessonScreen(
                    viewModel = viewModel,
                    bannerConfig = bannerConfig,
                    mediumRectangleConfig = mediumRectangleConfig,
                    onBack = { finish() },
                    onPlayClick = { playPause() },
                )
            }
        }
    }
}
