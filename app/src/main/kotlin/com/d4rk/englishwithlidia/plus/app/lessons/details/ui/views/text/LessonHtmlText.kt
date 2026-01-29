package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml

@Composable
fun LessonHtmlText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    color: Color,
) {
    val annotatedString = remember(text) { AnnotatedString.fromHtml(text) }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = style,
        color = color,
    )
}