/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.d4rk.android.libs.apptoolkit.app.theme.ui.style.AppTheme
import com.d4rk.android.libs.apptoolkit.core.ui.model.ads.AdsConfig
import com.d4rk.englishwithlidia.plus.player.ActivityPlayer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class LessonActivity : ActivityPlayer() {
    override val playbackHandler: LessonViewModel by viewModel()
    private val viewModel: LessonViewModel
        get() = playbackHandler
    private val bannerConfig: AdsConfig by inject()
    private val mediumRectangleConfig: AdsConfig by inject(named("banner_medium_rectangle"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val lessonId = intent?.data?.lastPathSegment
        lessonId?.let { viewModel.getLesson(it) }

        setContent {
            AppTheme {
                LessonRoute(
                    viewModel = viewModel,
                    bannerConfig = bannerConfig,
                    mediumRectangleConfig = mediumRectangleConfig,
                    onBack = { finish() },
                    onPlayClick = { playPause() },
                    onSeek = { positionInSeconds ->
                        seekTo((positionInSeconds * 1000).toLong())
                    },
                    onPreparePlayer = { content, lessonTitle, playWhenReady ->
                        preparePlayer(
                            audioUrl = content.contentAudioUrl,
                            title = content.contentTitle.ifBlank { lessonTitle },
                            thumbnailUrl = content.contentThumbnailUrl,
                            artist = content.contentArtist,
                            albumTitle = content.contentAlbumTitle,
                            genre = content.contentGenre,
                            description = content.contentDescription,
                            releaseYear = content.contentReleaseYear,
                            playWhenReady = playWhenReady,
                        )
                    },
                )
            }
        }
    }
}
