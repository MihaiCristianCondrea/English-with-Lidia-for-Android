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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.d4rk.englishwithlidia.plus.core.utils.constants.ui.lessons.LessonConstants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonListLayout(
    lessons: List<UiHomeLesson>,
    bannerAdsConfig: AdsConfig,
    mediumRectangleAdsConfig: AdsConfig,
    onLessonClick: (UiHomeLesson) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val items = remember(lessons) { buildAppListItems(lessons) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(SizeConstants.LargeSize),
        state = listState,
    ) {
        itemsIndexed(
            items = items,
            key = { _, item ->
                item.lesson.stableKey(item.contentType)
            },
            contentType = { _, item ->
                item.contentType
            },
        ) { index, item ->
            when (item) {
                is LessonListItem.BannerImage -> LessonBannerImage(
                    modifier = Modifier
                        .animateVisibility(index = index)
                        .animateItem(),
                )
                is LessonListItem.ActionButtons -> LessonActionButtonsRow(
                    modifier = Modifier
                        .animateVisibility(index = index)
                        .animateItem(),
                )
                is LessonListItem.BannerAd ->
                    BannerAdView(
                        adsConfig = bannerAdsConfig,
                    )

                is LessonListItem.MediumRectangleAd ->
                    MediumRectangleAdView(
                        adsConfig = mediumRectangleAdsConfig,
                    )

                is LessonListItem.Lesson -> LessonCardItem(
                    lesson = item.lesson,
                    onLessonClick = onLessonClick,
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
    onLessonClick: (UiHomeLesson) -> Unit,
    modifier: Modifier = Modifier,
) {
    LessonCard(
        title = lesson.lessonTitle,
        imageResource = lesson.lessonThumbnailImageUrl,
        onClick = { onLessonClick(lesson) },
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
    val lesson: UiHomeLesson
    val contentType: String

    data class BannerImage(override val lesson: UiHomeLesson) : LessonListItem {
        override val contentType: String = BannerImageContentType
    }

    data class ActionButtons(override val lesson: UiHomeLesson) : LessonListItem {
        override val contentType: String = ActionButtonsContentType
    }

    data class BannerAd(override val lesson: UiHomeLesson) : LessonListItem {
        override val contentType: String = BannerAdContentType
    }

    data class MediumRectangleAd(override val lesson: UiHomeLesson) : LessonListItem {
        override val contentType: String = MediumRectangleAdContentType
    }

    data class Lesson(override val lesson: UiHomeLesson) : LessonListItem {
        override val contentType: String = LessonContentType
    }
}

internal fun buildAppListItems(
    lessons: List<UiHomeLesson>,
): List<LessonListItem> = buildList {
    lessons.forEach { lesson ->
        when (lesson.lessonType) {
            LessonConstants.TYPE_BANNER_IMAGE_LOCAL -> add(LessonListItem.BannerImage(lesson))
            LessonConstants.TYPE_ROW_BUTTONS_LOCAL -> add(LessonListItem.ActionButtons(lesson))
            LessonConstants.TYPE_AD_VIEW_BANNER -> add(LessonListItem.BannerAd(lesson))
            LessonConstants.TYPE_AD_VIEW_BANNER_LARGE -> add(LessonListItem.MediumRectangleAd(lesson))
            else -> add(LessonListItem.Lesson(lesson))
        }
    }
}

private const val BannerImageContentType = "banner_image"
private const val ActionButtonsContentType = "action_buttons"
private const val BannerAdContentType = "banner_ad"
private const val MediumRectangleAdContentType = "medium_rectangle_ad"
private const val LessonContentType = "lesson"

private fun UiHomeLesson.stableKey(contentType: String): String {
    return lessonId.takeIf { it.isNotBlank() }
        ?: lessonDeepLinkPath.takeIf { it.isNotBlank() }
        ?: lessonTitle.takeIf { it.isNotBlank() }?.let { title ->
            "${lessonType.ifBlank { LessonContentType }}_$title"
        }
        ?: "${contentType}_${hashCode()}"
}
