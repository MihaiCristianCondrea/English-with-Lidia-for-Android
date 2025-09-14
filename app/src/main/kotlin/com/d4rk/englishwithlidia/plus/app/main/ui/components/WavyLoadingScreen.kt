package com.d4rk.englishwithlidia.plus.app.main.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WavyLoadingScreen(modifier: Modifier = Modifier) {
    val strokeWidth = with(LocalDensity.current) { 8.dp.toPx() }
    val stroke = remember(strokeWidth) { Stroke(width = strokeWidth, cap = StrokeCap.Round) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LinearWavyProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(14.dp),
            stroke = stroke,
            trackStroke = stroke,
        )
    }
}

