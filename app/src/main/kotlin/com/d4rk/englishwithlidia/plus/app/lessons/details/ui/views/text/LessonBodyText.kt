package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.views.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LessonBodyText(
    text: String,
    modifier: Modifier = Modifier,
) {
    LessonHtmlText(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )
}