package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.d4rk.android.libs.apptoolkit.core.domain.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.components.ads.AdBanner
import com.d4rk.android.libs.apptoolkit.core.ui.components.modifiers.animateVisibility
import com.d4rk.android.libs.apptoolkit.core.ui.components.modifiers.bounceClick
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.app.main.ui.components.navigation.openLessonDetailActivity
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonConstants
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonListLayout(
    lessons: List<UiHomeLesson>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val bannerConfig: AdsConfig = koinInject()
    val mediumRectangleConfig: AdsConfig =
        koinInject(qualifier = named(name = "banner_medium_rectangle"))
    val listState = rememberLazyListState()
    val items by remember(lessons) {
        derivedStateOf { buildAppListItems(lessons) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(SizeConstants.LargeSize),
        state = listState,
    ) {
        itemsIndexed(
            items = items,
            key = { index, item ->
                when (item) {
                    LessonListItem.BannerImage -> "banner_image_$index"
                    LessonListItem.ActionButtons -> "action_buttons_$index"
                    LessonListItem.BannerAd -> "banner_ad_$index"
                    LessonListItem.MediumRectangleAd -> "medium_rectangle_ad_$index"
                    is LessonListItem.Lesson ->
                        item.lesson.lessonId.ifBlank { "${item.lesson.lessonType}_$index" }
                }
            },
            contentType = { _, item ->
                when (item) {
                    LessonListItem.BannerImage -> "banner_image"
                    LessonListItem.ActionButtons -> "action_buttons"
                    LessonListItem.BannerAd -> "banner_ad"
                    LessonListItem.MediumRectangleAd -> "medium_rectangle_ad"
                    is LessonListItem.Lesson -> "lesson"
                }
            },
        ) { index, item ->
            when (item) {
                LessonListItem.BannerImage -> LessonBannerImage(
                    modifier = Modifier
                        .animateVisibility(index = index)
                        .animateItem(),
                )
                LessonListItem.ActionButtons -> LessonActionButtonsRow(
                    modifier = Modifier
                        .animateVisibility(index = index)
                        .animateItem(),
                )
                LessonListItem.BannerAd ->
                    BannerAdView(
                        adsConfig = bannerConfig,
                    )

                LessonListItem.MediumRectangleAd ->
                    MediumRectangleAdView(
                        adsConfig = mediumRectangleConfig,
                    )

                is LessonListItem.Lesson -> LessonCardItem(
                    lesson = item.lesson,
                    modifier = Modifier
                        .animateVisibility(index = index)
                        .animateItem()
                        .padding(horizontal = SizeConstants.LargeSize),
                )
            }
        }
    }
}

@Composable
private fun LessonBannerImage(modifier: Modifier = Modifier) {
    Image(
        imageVector = homeBanner(),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
private fun LessonActionButtonsRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, end = 24.dp),
    ) {
        OutlinedUrlButtons(
            url = "https://sites.google.com/view/englishwithlidia",
            text = R.string.website,
            modifier = Modifier
                .weight(1f)
                .bounceClick(),
            vectorIcon = Icons.Outlined.Language,
        )

        Spacer(modifier = Modifier.width(24.dp))

        OutlinedUrlButtons(
            url = "https://www.facebook.com/lidia.melinte",
            text = R.string.find_us,
            modifier = Modifier
                .weight(1f)
                .bounceClick(),
            painterIcon = painterResource(id = R.drawable.ic_find_us),
        )
    }
}

@Composable
private fun BannerAdView(
    adsConfig: AdsConfig,
    modifier: Modifier = Modifier,
) {
    AdBanner(
        modifier = modifier.fillMaxWidth(),
        adsConfig = adsConfig,
    )
}

@Composable
private fun MediumRectangleAdView(
    adsConfig: AdsConfig,
    modifier: Modifier = Modifier,
) {
    AdBanner(
        modifier = modifier.fillMaxWidth(),
        adsConfig = adsConfig,
    )
}

@Composable
private fun LessonCardItem(
    lesson: UiHomeLesson,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val onLessonClick = remember(context, lesson) {
        { openLessonDetailActivity(context = context, lesson = lesson) }
    }
    LessonCard(
        title = lesson.lessonTitle,
        imageResource = lesson.lessonThumbnailImageUrl,
        onClick = onLessonClick,
        modifier = modifier,
    )
}

@Composable
fun LessonCard(
    title: String,
    imageResource: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .bounceClick()
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .aspectRatio(ratio = 2.06f / 1f),
        ) {
            AsyncImage(
                model = imageResource,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SizeConstants.MediumSize),
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = SizeConstants.MediumSize))
    }
}

internal sealed interface LessonListItem {
    data object BannerImage : LessonListItem
    data object ActionButtons : LessonListItem
    data object BannerAd : LessonListItem
    data object MediumRectangleAd : LessonListItem
    data class Lesson(val lesson: UiHomeLesson) : LessonListItem
}

internal fun buildAppListItems(
    lessons: List<UiHomeLesson>,
): List<LessonListItem> = buildList {
    lessons.forEach { lesson ->
        when (lesson.lessonType) {
            LessonConstants.TYPE_BANNER_IMAGE_LOCAL -> add(LessonListItem.BannerImage)
            LessonConstants.TYPE_ROW_BUTTONS_LOCAL -> add(LessonListItem.ActionButtons)
            LessonConstants.TYPE_AD_VIEW_BANNER -> add(LessonListItem.BannerAd)
            LessonConstants.TYPE_AD_VIEW_BANNER_LARGE -> add(LessonListItem.MediumRectangleAd)
            else -> add(LessonListItem.Lesson(lesson))
        }
    }
}
