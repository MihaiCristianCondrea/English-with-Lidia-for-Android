package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.components.ads.AdBanner
import com.d4rk.android.libs.apptoolkit.core.ui.components.modifiers.bounceClick
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.LessonActivity
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.Colors
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.TextStyles
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonContentTypes
import com.d4rk.englishwithlidia.plus.R
import ir.mahozad.multiplatform.wavyslider.WaveDirection
import ir.mahozad.multiplatform.wavyslider.material3.WavySlider
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun LessonContentLayout(
    paddingValues : PaddingValues ,
    scrollState : ScrollState ,
    lesson : UiLessonScreen ,
    activity: LessonActivity ,
) {
    val bannerConfig: AdsConfig = koinInject()
    val mediumRectangleConfig: AdsConfig = koinInject(qualifier = named(name = "banner_medium_rectangle"))

    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
    ) {
        lesson.lessonContent.forEachIndexed { index , contentItem ->
            when (contentItem.contentType) {
                LessonContentTypes.HEADER -> {
                    StyledText(
                        text = contentItem.contentText ,
                        style = TextStyles.header() ,
                        color = Colors.primaryText()
                    )
                }

                LessonContentTypes.TEXT -> {
                    StyledText(
                        text = contentItem.contentText ,
                        style = TextStyles.body() ,
                        color = Colors.secondaryText()
                    )
                }

                LessonContentTypes.CONTENT_PLAYER -> {
                    val sliderPosition = lesson.playbackPosition
                    val playbackDuration = lesson.playbackDuration

                    val isPlaying = lesson.isPlaying

                    LaunchedEffect(key1 = contentItem.contentAudioUrl) {
                        activity.preparePlayer(
                            audioUrl = contentItem.contentAudioUrl,
                            title = contentItem.contentTitle.ifBlank { lesson.lessonTitle },
                            thumbnailUrl = contentItem.contentThumbnailUrl,
                            artist = contentItem.contentArtist,
                            albumTitle = contentItem.contentAlbumTitle,
                            genre = contentItem.contentGenre,
                            description = contentItem.contentDescription,
                            releaseYear = contentItem.contentReleaseYear
                        )
                    }

                    AudioCardView(onPlayClick = { activity.playPause() } ,
                                  onSeekChange = { newPosition ->
                                      activity.seekTo((newPosition * 1000).toLong())
                                  } ,
                                  sliderPosition = sliderPosition.toFloat() / 1000f ,
                                  playbackDuration = playbackDuration.toFloat() / 1000f ,
                                  isPlaying = isPlaying)
                }

                LessonContentTypes.TYPE_DIVIDER -> {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }

                LessonContentTypes.IMAGE -> {
                    StyledImage(
                        imageUrl = contentItem.contentImageUrl , contentDescription = "Lesson Image"
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
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AdBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adsConfig = bannerConfig
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
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AdBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adsConfig = mediumRectangleConfig
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                else -> {
                    Text(text = "Unsupported content type: ${contentItem.contentType}")
                }
            }
            if (index < lesson.lessonContent.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun StyledText(
    text : String ,
    style : TextStyle = TextStyles.body() ,
    color : Color = Colors.primaryText() ,
) {
    val annotatedString = AnnotatedString.fromHtml(text)

    Text(
        text = annotatedString , style = style , color = color
    )
}

@Composable
fun StyledImage(
    modifier : Modifier = Modifier ,
    imageUrl : String ,
    contentDescription : String? = null ,
) {
    Card(
        modifier = modifier.fillMaxWidth() ,
    ) {
        AsyncImage(
            model = imageUrl ,
            contentScale = ContentScale.FillWidth ,
            contentDescription = contentDescription ,
            modifier = Modifier.fillMaxWidth() ,
        )
    }
}

@Composable
fun AudioCardView(
    onPlayClick : () -> Unit ,
    onSeekChange : (Float) -> Unit ,
    sliderPosition : Float ,
    playbackDuration : Float ,
    isPlaying : Boolean ,
) {
    val cornerRadius = animateFloatAsState(
        targetValue = if (isPlaying) 16f else 28f ,
        animationSpec = tween(durationMillis = 200) ,
        label = ""
    ).value

    var sliderValue = if (playbackDuration > 0f) sliderPosition / playbackDuration else 0f

    OutlinedCard(
        modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp) , shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth() ,
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = onPlayClick ,
                    modifier = Modifier
                            .bounceClick() ,
                    shape = RoundedCornerShape(cornerRadius.dp)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow ,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                WavySlider(value = sliderValue ,
                           onValueChange = { newValue ->
                               sliderValue = newValue
                               if (playbackDuration > 0f) {
                                   val newPosition = newValue * playbackDuration
                                   onSeekChange(newPosition)
                               }
                           } ,
                           waveLength = 24.dp ,
                           waveHeight = 4.dp ,
                           waveVelocity = 4.dp to WaveDirection.HEAD ,
                           waveThickness = 4.dp ,
                           trackThickness = 4.dp ,
                           incremental = false ,
                           modifier = Modifier
                                   .fillMaxWidth()
                                   .weight(weight = 4f))
            }
        }
    }
}