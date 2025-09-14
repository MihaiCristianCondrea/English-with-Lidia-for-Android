package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun LessonListLayout(
    lessons : List<UiHomeLesson> , paddingValues : PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        itemsIndexed(lessons) { index , lesson ->
            LessonItem(
                lesson = lesson ,
                modifier = Modifier
                        .animateVisibility()
                        .animateItem()
            )
        }
    }
}

@Composable
fun LessonItem(lesson : UiHomeLesson,  modifier : Modifier = Modifier) {
    val context = LocalContext.current
    val bannerConfig: AdsConfig = koinInject()
    val mediumRectangleConfig: AdsConfig = koinInject(qualifier = named(name = "banner_medium_rectangle"))


    when (lesson.lessonType) {
        LessonConstants.TYPE_BANNER_IMAGE_LOCAL -> {
            Image(
                imageVector = homeBanner() ,
                contentDescription = null ,
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        LessonConstants.TYPE_ROW_BUTTONS_LOCAL -> {
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp , end = 24.dp) ,
            ) {
                OutlinedUrlButtons(
                    vectorIcon = Icons.Outlined.Language ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    text = R.string.website ,
                    url = "https://sites.google.com/view/englishwithlidia"
                )

                Spacer(modifier = Modifier.width(24.dp))

                OutlinedUrlButtons(
                    painterIcon = painterResource(id = R.drawable.ic_find_us) ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    text = R.string.find_us ,
                    url = "https://www.facebook.com/lidia.melinte"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        LessonConstants.TYPE_AD_VIEW_BANNER -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.ad_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                AdBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SizeConstants.MediumSize),
                    adsConfig = bannerConfig
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        LessonConstants.TYPE_AD_VIEW_BANNER_LARGE -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.ad_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                AdBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SizeConstants.MediumSize),
                    adsConfig = mediumRectangleConfig
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        LessonConstants.TYPE_FULL_IMAGE_BANNER -> {
            LessonCard(title = lesson.lessonTitle ,
                       imageResource = lesson.lessonThumbnailImageUrl ,
                       onClick = {
                           openLessonDetailActivity(context = context , lesson = lesson)
                       } ,
                       modifier = modifier)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun LessonCard(
    title : String , imageResource : String , onClick : () -> Unit , modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) ,
    ) {
        Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onClick)
                    .aspectRatio(ratio = 2.06f / 1f) ,
        ) {
            AsyncImage(
                model = imageResource ,
                contentDescription = null ,
                modifier = Modifier.fillMaxSize() ,
                contentScale = ContentScale.Crop ,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title ,
            style = MaterialTheme.typography.headlineMedium ,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
    }
}