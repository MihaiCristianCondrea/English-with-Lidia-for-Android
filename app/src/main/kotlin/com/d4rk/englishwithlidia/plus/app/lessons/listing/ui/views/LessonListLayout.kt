package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.d4rk.android.libs.apptoolkit.core.ui.model.ads.AdsConfig
import com.d4rk.android.libs.apptoolkit.core.ui.views.modifiers.animateVisibility
import com.d4rk.android.libs.apptoolkit.core.utils.constants.ui.SizeConstants
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.mapping.buildListingListItems
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.mapping.listingListItemContentType
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.state.ListingLessonUiModel
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views.ads.BannerAdView
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views.ads.MediumRectangleAdView
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views.cards.LessonCard
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonListLayout(
    lessons: ImmutableList<ListingLessonUiModel>,
    bannerAdsConfig: AdsConfig,
    mediumRectangleAdsConfig: AdsConfig,
    onLessonClick: (ListingLessonUiModel) -> Unit,
    paddingValues: PaddingValues,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val listItems = remember(lessons) { buildListingListItems(lessons) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SizeConstants.LargeSize),
        state = listState,
        contentPadding = paddingValues,
    ) {
        itemsIndexed(
            items = listItems,
            key = ::lessonListItemKey,
            contentType = { _, item -> listingListItemContentType(item) },
        ) { index, item ->
            LessonListEntry(
                index = index,
                item = item,
                onLessonClick = onLessonClick,
                bannerAdsConfig = bannerAdsConfig,
                mediumRectangleAdsConfig = mediumRectangleAdsConfig,
            )
        }
    }
}

@Immutable
internal sealed interface LessonListItem {
    data object BannerImage : LessonListItem
    data object ActionButtons : LessonListItem
    data object BannerAd : LessonListItem
    data object MediumRectangleAd : LessonListItem
    data class Lesson(val lesson: ListingLessonUiModel) : LessonListItem
}

private fun lessonListItemKey(index: Int, item: LessonListItem): Any = when (item) {
    LessonListItem.BannerImage -> "banner_image_$index"
    LessonListItem.ActionButtons -> "action_buttons_$index"
    LessonListItem.BannerAd -> "banner_ad_$index"
    LessonListItem.MediumRectangleAd -> "medium_rectangle_ad_$index"
    is LessonListItem.Lesson -> item.lesson.lessonId.ifBlank { "${item.lesson.lessonType}_$index" }
}

@Composable
private fun LazyItemScope.LessonListEntry(
    index: Int,
    item: LessonListItem,
    onLessonClick: (ListingLessonUiModel) -> Unit,
    bannerAdsConfig: AdsConfig,
    mediumRectangleAdsConfig: AdsConfig,
) {
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

        LessonListItem.BannerAd -> BannerAdView(adsConfig = bannerAdsConfig)

        LessonListItem.MediumRectangleAd -> MediumRectangleAdView(
            adsConfig = mediumRectangleAdsConfig,
        )

        is LessonListItem.Lesson -> LessonCard(
            title = item.lesson.lessonTitle,
            imageResource = item.lesson.lessonThumbnailImageUrl,
            onClick = { onLessonClick(item.lesson) },
            modifier = Modifier
                .animateVisibility(index = index)
                .animateItem()
                .padding(horizontal = SizeConstants.LargeSize),
        )
    }
}
