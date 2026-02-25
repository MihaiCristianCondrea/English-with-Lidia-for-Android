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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.d4rk.android.libs.apptoolkit.core.ui.views.modifiers.bounceClick
import com.d4rk.englishwithlidia.plus.R

@Composable
fun LessonActionButtonsRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UrlActionButton(
            url = "https://sites.google.com/view/englishwithlidia",
            text = R.string.website,
            modifier = Modifier
                .weight(1f)
                .bounceClick(),
            vectorIcon = Icons.Outlined.Language,
        )

        UrlActionButton(
            url = "https://www.facebook.com/lidia.melinte",
            text = R.string.find_us,
            modifier = Modifier
                .weight(1f)
                .bounceClick(),
            painterIcon = painterResource(id = R.drawable.ic_find_us),
        )
    }
}