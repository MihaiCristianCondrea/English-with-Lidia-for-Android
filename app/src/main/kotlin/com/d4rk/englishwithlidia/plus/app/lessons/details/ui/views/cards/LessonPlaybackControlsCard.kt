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

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.d4rk.android.libs.apptoolkit.core.ui.views.spacers.ExtraSmallVerticalSpacer
import com.d4rk.android.libs.apptoolkit.core.ui.views.modifiers.bounceClick
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.LessonPlaybackUiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LessonAudioContent(
    playbackState: LessonPlaybackUiState,
    onPlayClick: () -> Unit,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val playButtonCorner by animateFloatAsState(
        targetValue = if (playbackState.isPlaying || playbackState.isBuffering) 16f else 28f,
        animationSpec = tween(durationMillis = 200),
        label = "play_button_corner",
    )
    val bottomCornerRadius by animateDpAsState(
        targetValue = if (playbackState.hasPlaybackError) {
            SizeConstants.ExtraSmallSize
        } else {
            SizeConstants.ExtraLargeSize
        },
        animationSpec = tween(durationMillis = 200),
        label = "playback_corner",
    )

    val sliderMax = remember(playbackState.playbackDuration) {
        playbackState.playbackDuration.takeIf { it > 0f } ?: 1f
    }
    val sliderRange = remember(sliderMax) { 0f..sliderMax }

    val targetSliderValue by remember(
        playbackState.sliderPosition,
        sliderRange,
    ) {
        derivedStateOf {
            playbackState.sliderPosition.coerceIn(sliderRange.start, sliderRange.endInclusive)
        }
    }

    var sliderValue by remember(sliderRange.endInclusive) {
        mutableFloatStateOf(targetSliderValue)
    }
    var isDragging by remember(sliderRange.endInclusive) { mutableStateOf(false) }

    val isSliderEnabled by remember(
        playbackState.playbackDuration,
        playbackState.hasPlaybackError,
    ) {
        derivedStateOf { playbackState.playbackDuration > 0f && !playbackState.hasPlaybackError }
    }

    LaunchedEffect(targetSliderValue, sliderRange.endInclusive, isDragging) {
        if (!isDragging) {
            sliderValue = targetSliderValue
        }
    }

    LaunchedEffect(isSliderEnabled, targetSliderValue, sliderRange.endInclusive) {
        if (!isSliderEnabled) {
            isDragging = false
            sliderValue = targetSliderValue
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        LessonPlaybackControlsCard(
            onPlayClick = onPlayClick,
            sliderValue = sliderValue,
            sliderRange = sliderRange,
            isSliderEnabled = isSliderEnabled,
            isPlaying = playbackState.isPlaying,
            isBuffering = playbackState.isBuffering,
            onSliderValueChange = { value ->
                isDragging = true
                sliderValue = value
            },
            onSliderValueChangeFinished = {
                val targetValue = sliderValue.coerceIn(sliderRange.start, sliderRange.endInclusive)
                sliderValue = targetValue
                onSeek(targetValue)
                isDragging = false
            },
            playButtonCorner = playButtonCorner,
            bottomCornerRadius = bottomCornerRadius,
            showError = playbackState.hasPlaybackError,
        )

        LessonPlaybackErrorMessage(isVisible = playbackState.hasPlaybackError)
    }
}

@Composable
fun LessonPlaybackErrorMessage(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ExtraSmallVerticalSpacer()

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeConstants.SmallSize)
                    .padding(bottom = SizeConstants.SmallSize),
                shape = RoundedCornerShape(
                    topStart = SizeConstants.ExtraSmallSize,
                    topEnd = SizeConstants.ExtraSmallSize,
                    bottomStart = SizeConstants.ExtraLargeSize,
                    bottomEnd = SizeConstants.ExtraLargeSize,
                ),
            ) {
                Text(
                    text = "Playback unavailable",
                    modifier = Modifier
                        .padding(SizeConstants.LargeSize)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
fun LessonPlaybackControlsCard(
    onPlayClick: () -> Unit,
    sliderValue: Float,
    sliderRange: ClosedFloatingPointRange<Float>,
    isSliderEnabled: Boolean,
    isPlaying: Boolean,
    isBuffering: Boolean,
    onSliderValueChange: (Float) -> Unit,
    onSliderValueChangeFinished: () -> Unit,
    playButtonCorner: Float,
    bottomCornerRadius: Dp,
    showError: Boolean,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SizeConstants.SmallSize)
            .padding(
                top = SizeConstants.SmallSize,
                bottom = if (showError) SizeConstants.ZeroSize else SizeConstants.SmallSize,
            ),
        shape = RoundedCornerShape(
            topStart = SizeConstants.ExtraLargeSize,
            topEnd = SizeConstants.ExtraLargeSize,
            bottomStart = bottomCornerRadius,
            bottomEnd = bottomCornerRadius,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(SizeConstants.LargeSize)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                FloatingActionButton(
                    onClick = onPlayClick,
                    modifier = Modifier
                        .bounceClick(),
                    shape = RoundedCornerShape(playButtonCorner.dp),
                ) {
                    if (isBuffering) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(SizeConstants.TwentyFourSize),
                            strokeWidth = SizeConstants.ExtraTinySize,
                            color = LocalContentColor.current,
                        )
                    } else {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                        )
                    }
                }

                Spacer(modifier = Modifier.width(SizeConstants.LargeSize))

                Slider(
                    value = sliderValue,
                    onValueChange = onSliderValueChange,
                    modifier = Modifier
                        .weight(4f)
                        .fillMaxWidth(),
                    enabled = isSliderEnabled,
                    valueRange = sliderRange,
                    onValueChangeFinished = onSliderValueChangeFinished,
                )
            }
        }
    }
}
