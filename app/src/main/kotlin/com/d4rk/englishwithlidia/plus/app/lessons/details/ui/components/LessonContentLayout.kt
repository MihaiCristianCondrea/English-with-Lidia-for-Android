package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.components.ads.AdBanner
import com.d4rk.android.libs.apptoolkit.core.ui.components.modifiers.animateVisibility
import com.d4rk.android.libs.apptoolkit.core.ui.components.modifiers.bounceClick
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.Colors
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.TextStyles
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
    val playbackState = LessonPlaybackUiState(
        sliderPosition = lesson.playbackPosition.toSeconds(),
        playbackDuration = lesson.playbackDuration.toSeconds(),
        isPlaying = lesson.isPlaying,
        isBuffering = lesson.isBuffering,
        hasPlaybackError = lesson.hasPlaybackError,
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        state = listState,
        contentPadding = PaddingValues(horizontal = SizeConstants.LargeSize),
        verticalArrangement = Arrangement.spacedBy(SizeConstants.MediumSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = lesson.lessonContent,
            key = { it.contentId },
        ) { contentItem ->
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
    }
}

@Composable
private fun LessonHeaderText(
    text: String,
    modifier: Modifier = Modifier,
) {
    LessonHtmlText(
        modifier = modifier,
        text = text,
        style = TextStyles.header(),
        color = Colors.primaryText(),
    )
}

@Composable
private fun LessonBodyText(
    text: String,
    modifier: Modifier = Modifier,
) {
    LessonHtmlText(
        modifier = modifier,
        text = text,
        style = TextStyles.body(),
        color = Colors.secondaryText(),
    )
}

@Composable
private fun LessonHtmlText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    color: Color,
) {
    val annotatedString = remember(text) { AnnotatedString.fromHtml(text) }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = style,
        color = color,
    )
}

@Composable
private fun LessonImageContent(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = imageUrl,
            contentScale = ContentScale.FillWidth,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun LessonAdBanner(
    adsConfig: AdsConfig,
    modifier: Modifier = Modifier,
) {
    AdBanner(
        modifier = modifier.fillMaxWidth(),
        adsConfig = adsConfig,
    )
}

@Composable
private fun LessonContentDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier.fillMaxWidth())
}

@Composable
private fun LessonUnsupportedContent(
    contentType: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Unsupported content type: $contentType",
        modifier = modifier.fillMaxWidth(),
        style = TextStyles.body(),
        color = Colors.secondaryText(),
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LessonAudioContent(
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
        targetValue = if (playbackState.hasPlaybackError) 4.dp else 28.dp,
        animationSpec = tween(durationMillis = 200),
        label = "playback_corner",
    )

    Column(modifier = modifier.fillMaxWidth()) {
        LessonPlaybackControlsCard(
            onPlayClick = onPlayClick,
            sliderPosition = playbackState.sliderPosition,
            playbackDuration = playbackState.playbackDuration,
            isPlaying = playbackState.isPlaying,
            isBuffering = playbackState.isBuffering,
            onSeek = onSeek,
            playButtonCorner = playButtonCorner,
            bottomCornerRadius = bottomCornerRadius,
            showError = playbackState.hasPlaybackError,
        )

        LessonPlaybackErrorMessage(isVisible = playbackState.hasPlaybackError)
    }
}

@Composable
private fun LessonPlaybackControlsCard(
    onPlayClick: () -> Unit,
    sliderPosition: Float,
    playbackDuration: Float,
    isPlaying: Boolean,
    isBuffering: Boolean,
    onSeek: (Float) -> Unit,
    playButtonCorner: Float,
    bottomCornerRadius: Dp,
    showError: Boolean,
) {
    val sliderMax = if (playbackDuration > 0f) playbackDuration else 1f
    var sliderValue by remember { mutableFloatStateOf(sliderPosition.coerceIn(0f, sliderMax)) }
    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(sliderPosition, sliderMax) {
        if (!isDragging) {
            sliderValue = sliderPosition.coerceIn(0f, sliderMax)
        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp, bottom = if (showError) 0.dp else 8.dp),
        shape = RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp,
            bottomStart = bottomCornerRadius,
            bottomEnd = bottomCornerRadius,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
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

                Slider(
                    value = sliderValue,
                    onValueChange = { value ->
                        isDragging = true
                        sliderValue = value
                    },
                    modifier = Modifier
                        .weight(4f)
                        .fillMaxWidth(),
                    enabled = playbackDuration > 0f,
                    valueRange = 0f..sliderMax,
                    onValueChangeFinished = {
                        val targetValue = sliderValue.coerceIn(0f, sliderMax)
                        sliderValue = targetValue
                        onSeek(targetValue)
                        isDragging = false
                    },
                )
            }
        }
    }
}

@Composable
private fun LessonPlaybackErrorMessage(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 4.dp,
                    bottomStart = 28.dp,
                    bottomEnd = 28.dp,
                ),
            ) {
                Text(
                    text = "Playback unavailable",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = TextStyles.body(),
                    color = Colors.secondaryText(),
                )
            }
        }
    }
}

@Immutable
private data class LessonPlaybackUiState(
    val sliderPosition: Float,
    val playbackDuration: Float,
    val isPlaying: Boolean,
    val isBuffering: Boolean,
    val hasPlaybackError: Boolean,
)

private fun Long.toSeconds(): Float = (this.toFloat() / 1000f).coerceAtLeast(0f)
