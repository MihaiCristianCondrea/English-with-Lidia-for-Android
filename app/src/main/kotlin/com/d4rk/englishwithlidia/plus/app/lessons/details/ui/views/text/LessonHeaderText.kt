package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.Colors
import com.d4rk.englishwithlidia.plus.app.settings.display.theme.style.TextStyles

@Composable
fun LessonHeaderText(
    text: String,
    modifier: Modifier = Modifier,
) {
    LessonHtmlText(
        modifier = modifier,
        text = text,
        style = TextStyles.header(),
        color = Colors.primaryText(),
    )
}