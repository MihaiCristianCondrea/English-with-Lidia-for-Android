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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.d4rk.android.libs.apptoolkit.core.ui.views.modifiers.bounceClick
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.LessonPlaybackUiState
import java.util.Locale

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LessonAudioContent(
    contentItem: UiLessonContent,
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
        targetValue = if (playbackState.hasPlaybackError) 4.dp else 24.dp,
        animationSpec = tween(durationMillis = 200),
        label = "playback_corner",
    )

    val sliderMax = remember(playbackState.playbackDuration) {
        playbackState.playbackDuration.takeIf { it > 0f } ?: 1f
    }
    val sliderRange = remember(sliderMax) { 0f..sliderMax }

    val targetSliderValue by remember(playbackState.sliderPosition, sliderRange) {
        derivedStateOf { playbackState.sliderPosition.coerceIn(sliderRange.start, sliderRange.endInclusive) }
    }

    var sliderValue by remember(sliderRange.endInclusive) { mutableFloatStateOf(targetSliderValue) }
    var isDragging by remember(sliderRange.endInclusive) { mutableStateOf(false) }

    val isSliderEnabled by remember(playbackState.playbackDuration, playbackState.hasPlaybackError) {
        derivedStateOf { playbackState.playbackDuration > 0f && !playbackState.hasPlaybackError }
    }

    LaunchedEffect(targetSliderValue, sliderRange.endInclusive, isDragging) {
        if (!isDragging) sliderValue = targetSliderValue
    }

    LaunchedEffect(isSliderEnabled, targetSliderValue, sliderRange.endInclusive) {
        if (!isSliderEnabled) {
            isDragging = false
            sliderValue = targetSliderValue
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        LessonPlaybackControlsCard(
            contentItem = contentItem,
            onPlayClick = onPlayClick,
            sliderValue = sliderValue,
            sliderMax = sliderMax,
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
fun LessonPlaybackControlsCard(
    contentItem: UiLessonContent,
    onPlayClick: () -> Unit,
    sliderValue: Float,
    sliderMax: Float,
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SizeConstants.SmallSize, bottom = if (showError) 0.dp else SizeConstants.SmallSize),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp,
            bottomStart = bottomCornerRadius,
            bottomEnd = bottomCornerRadius,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            // NEW: Media Info Row (Thumbnail, Title, Artist)
            if (contentItem.contentTitle.isNotBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (contentItem.contentThumbnailUrl.isNotBlank()) {
                        AsyncImage(
                            model = contentItem.contentThumbnailUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = contentItem.contentTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (contentItem.contentArtist.isNotBlank()) {
                            Text(
                                text = contentItem.contentArtist,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Controls Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FloatingActionButton(
                    onClick = onPlayClick,
                    modifier = Modifier.bounceClick(),
                    shape = RoundedCornerShape(playButtonCorner.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    if (isBuffering) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = LocalContentColor.current,
                        )
                    } else {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Slider(
                        value = sliderValue,
                        onValueChange = onSliderValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isSliderEnabled,
                        valueRange = sliderRange,
                        onValueChangeFinished = onSliderValueChangeFinished,
                    )

                    // NEW: Time duration text below slider
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatTime(sliderValue),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formatTime(sliderMax),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LessonPlaybackErrorMessage(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SizeConstants.SmallSize)
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(
                        topStart = 4.dp, topEnd = 4.dp,
                        bottomStart = 24.dp, bottomEnd = 24.dp,
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Playback unavailable. Please check your connection.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
    }
}

// Helper for formatting M:SS
private fun formatTime(seconds: Float): String {
    if (seconds.isNaN() || seconds < 0f) return "0:00"
    val totalSeconds = seconds.toInt()
    val m = totalSeconds / 60
    val s = totalSeconds % 60
    return String.format(Locale.getDefault(), "%d:%02d", m, s)
}