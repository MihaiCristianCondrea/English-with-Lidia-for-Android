package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.views

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.context.openUrl

@Composable
fun OutlinedUrlButtons(
    url: String,
    text: Int,
    modifier: Modifier = Modifier,
    vectorIcon: ImageVector? = null,
    painterIcon: Painter? = null,
) {
    val context = LocalContext.current

    OutlinedButton(
        onClick = { context.openUrl(url = url) },
        modifier = modifier,
    ) {
        if (painterIcon != null) {
            Icon(
                painter = painterIcon,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
        } else if (vectorIcon != null) {
            Icon(
                imageVector = vectorIcon,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = stringResource(id = text), modifier = Modifier.basicMarquee())
    }
}
