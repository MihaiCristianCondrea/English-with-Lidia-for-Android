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

package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views.drawable.listingBanner

@Composable
fun LessonBannerImage(
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val parallaxOffset = if (listState.firstVisibleItemIndex == 0) {
        -(listState.firstVisibleItemScrollOffset * PARALLAX_SCROLL_FACTOR)
    } else {
        0f
    }

    Image(
        imageVector = listingBanner(),
        contentDescription = null,
        modifier = modifier
            .graphicsLayer { translationY = parallaxOffset }
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)), // Softens the edge
        contentScale = ContentScale.FillWidth,
    )
}

private const val PARALLAX_SCROLL_FACTOR = 0.5f