package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.Colors
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.TextStyles

@Composable
fun LessonBodyText(
    text: String,
    modifier: Modifier = Modifier,
) {
    LessonHtmlText(
        modifier = modifier,
        text = text,
        style = TextStyles.body(),
        color = Colors.secondaryText(),
    )
}