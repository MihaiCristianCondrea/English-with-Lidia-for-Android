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

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.d4rk.android.libs.apptoolkit.core.ui.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.views.modifiers.animateVisibility
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.cards.LessonAudioContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.text.LessonBodyText
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.text.LessonHeaderText
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonContentTypes

@Composable
fun LessonContentLayout(
    paddingValues: PaddingValues,
    listState: LazyListState,
    lesson: UiLessonScreen,
    bannerConfig: AdsConfig,
    mediumRectangleConfig: AdsConfig,
    onPlayClick: () -> Unit,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val playbackState by remember(
        lesson.playbackPosition,
        lesson.playbackDuration,
        lesson.isPlaying,
        lesson.isBuffering,
        lesson.hasPlaybackError,
    ) {
        derivedStateOf {
            LessonPlaybackUiState(
                sliderPosition = lesson.playbackPosition.toSeconds(),
                playbackDuration = lesson.playbackDuration.toSeconds(),
                isPlaying = lesson.isPlaying,
                isBuffering = lesson.isBuffering,
                hasPlaybackError = lesson.hasPlaybackError,
            )
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        state = listState,
        contentPadding = PaddingValues(horizontal = SizeConstants.LargeSize),
        verticalArrangement = Arrangement.spacedBy(SizeConstants.MediumSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(
            items = lesson.lessonContent,
            key = { index, content ->
                content.contentId.takeIf { it.isNotBlank() }
                    ?: "${content.contentType}_${content.hashCode()}_$index"
            },
            contentType = { _, content -> content.contentType },
        ) { _, contentItem ->
            when (contentItem.contentType) {
                LessonContentTypes.HEADER -> LessonHeaderText(
                    text = contentItem.contentText,
                    modifier = Modifier
                        .animateItem()
                        .animateVisibility(),
                )

                LessonContentTypes.TEXT -> LessonBodyText(
                    text = contentItem.contentText,
                    modifier = Modifier
                        .animateItem()
                        .animateVisibility(),
                )

                LessonContentTypes.CONTENT_PLAYER -> LessonAudioContent(
                    modifier = Modifier
                        .animateItem()
                        .animateVisibility(),
                    playbackState = playbackState,
                    onPlayClick = onPlayClick,
                    onSeek = onSeek,
                )

                LessonContentTypes.TYPE_DIVIDER -> LessonContentDivider()

                LessonContentTypes.IMAGE -> LessonImageContent(
                    imageUrl = contentItem.contentImageUrl,
                    modifier = Modifier
                        .animateItem()
                        .animateVisibility(),
                )

                LessonContentTypes.AD_BANNER -> LessonAdBanner(adsConfig = bannerConfig)

                LessonContentTypes.AD_LARGE_BANNER -> LessonAdBanner(adsConfig = mediumRectangleConfig)

                else -> LessonUnsupportedContent(contentType = contentItem.contentType)
            }
        }


        if (lesson.writer.isNotBlank()) {
            item(key = "lesson_writer_footer") {
                LessonBodyText(
                    text = stringResource(R.string.lesson_writer_footer, lesson.writer),
                    modifier = Modifier
                        .animateItem()
                        .animateVisibility(),
                )
            }
        }
    }
}

// TODO: To ui model
@Immutable
data class LessonPlaybackUiState(
    val sliderPosition: Float,
    val playbackDuration: Float,
    val isPlaying: Boolean,
    val isBuffering: Boolean,
    val hasPlaybackError: Boolean,
)

// TODO: To mapping
fun Long.toSeconds(): Float = (this.toFloat() / 1000f).coerceAtLeast(0f)
