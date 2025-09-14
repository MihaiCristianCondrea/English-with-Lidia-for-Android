package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.components.ads.AdBanner
import com.d4rk.android.libs.apptoolkit.core.ui.components.modifiers.bounceClick
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.R
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
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        state = listState,
        contentPadding = PaddingValues(horizontal = SizeConstants.LargeSize),
        verticalArrangement = Arrangement.spacedBy(SizeConstants.SmallSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(items = lesson.lessonContent, key = { _, item -> item.contentId }) { index, contentItem ->
            when (contentItem.contentType) {
                LessonContentTypes.HEADER -> {
                    StyledText(
                        text = contentItem.contentText,
                        style = TextStyles.header(),
                        color = Colors.primaryText(),
                    )
                }

                LessonContentTypes.TEXT -> {
                    StyledText(
                        text = contentItem.contentText,
                        style = TextStyles.body(),
                        color = Colors.secondaryText(),
                    )
                }

                LessonContentTypes.CONTENT_PLAYER -> {
                    val sliderPosition = lesson.playbackPosition
                    val playbackDuration = lesson.playbackDuration
                    val isPlaying = lesson.isPlaying

                    AudioCardView(
                        onPlayClick = onPlayClick,
                        sliderPosition = sliderPosition.toFloat() / 1000f,
                        playbackDuration = playbackDuration.toFloat() / 1000f,
                        isPlaying = isPlaying,
                    )
                }

                LessonContentTypes.TYPE_DIVIDER -> {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }

                LessonContentTypes.IMAGE -> {
                    StyledImage(
                        imageUrl = contentItem.contentImageUrl,
                        contentDescription = "Lesson Image",
                    )
                }

                LessonContentTypes.AD_BANNER -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SizeConstants.MediumSize)
                    ) {
                        Text(
                            text = stringResource(id = R.string.ad_label),
                            style = TextStyles.label(),
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AdBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adsConfig = bannerConfig,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                LessonContentTypes.AD_LARGE_BANNER -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SizeConstants.MediumSize)
                    ) {
                        Text(
                            text = stringResource(id = R.string.ad_label),
                            style = TextStyles.label(),
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AdBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adsConfig = mediumRectangleConfig,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                else -> {
                    Text(text = "Unsupported content type: ${contentItem.contentType}")
                }
            }
        }
    }
}

@Composable
fun StyledText(
    text: String,
    style: TextStyle = TextStyles.body(),
    color: Color = Colors.primaryText(),
) {
    val annotatedString = remember(text) { AnnotatedString.fromHtml(text) }

    Text(
        text = annotatedString,
        style = style,
        color = color,
    )
}

@Composable
fun StyledImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AudioCardView(
    onPlayClick: () -> Unit,
    sliderPosition: Float,
    playbackDuration: Float,
    isPlaying: Boolean,
) {
    val progress = if (playbackDuration > 0f) {
        sliderPosition / playbackDuration
    } else {
        0f
    }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "",
    )
    val cornerRadius = animateFloatAsState(
        targetValue = if (isPlaying) 16f else 28f,
        animationSpec = tween(durationMillis = 200),
        label = "",
    ).value

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(28.dp),
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
                    shape = RoundedCornerShape(cornerRadius.dp),
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                LinearWavyProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f),
                    amplitude = { if (isPlaying) 1f else 0f },
                )
            }
        }
    }
}

