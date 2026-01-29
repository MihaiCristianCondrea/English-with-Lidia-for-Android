package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.Colors
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.TextStyles

@Composable
fun LessonUnsupportedContent(
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