package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun homeBanner(
    colorPrimary: Color = MaterialTheme.colorScheme.secondaryContainer,
    colorTertiary: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    colorSecondaryContainer: Color = MaterialTheme.colorScheme.primary,
    colorPrimaryContainer: Color = MaterialTheme.colorScheme.tertiary,
    colorOnSecondary: Color = MaterialTheme.colorScheme.primaryContainer,
    colorOnPrimary: Color = MaterialTheme.colorScheme.inverseSurface
): ImageVector {
    return remember(
        colorPrimary,
        colorTertiary,
        colorSecondaryContainer,
        colorPrimaryContainer,
        colorOnSecondary,
        colorOnPrimary
    ) {
        ImageVector.Builder(
            name = "Home Banner",
            defaultWidth = 640.dp,
            defaultHeight = 360.dp,
            viewportWidth = 640f,
            viewportHeight = 360f
        ).apply {
            path(
                fill = SolidColor(colorPrimary),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(0f, 0f)
                horizontalLineTo(640f)
                verticalLineTo(360f)
                horizontalLineTo(0f)
                close()
                moveTo(0f, 0f)
                lineTo(0f, 353f)
                curveTo(273.5f, 94.4f, 435.7f, 218.7f, 640f, 139.6f)
                lineTo(640f, 0f)
                lineTo(0f, 0f)
                close()
            }

            path(
                fill = SolidColor(colorTertiary),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(458.8f, 360.3f)
                lineTo(570.6f, 360.3f)
                curveTo(570.6f, 360.3f, 565.2f, 317f, 569f, 295.1f)
                curveTo(575.1f, 293.8f, 582f, 363f, 583.1f, 360.3f)
                lineTo(635.1f, 360.3f)
                curveTo(578.1f, 215.2f, 591.9f, 235.3f, 447.6f, 246.4f)
                curveTo(447.6f, 246.4f, 424.5f, 274.4f, 415.1f, 300.8f)
                curveTo(405.7f, 327.2f, 391.7f, 352.7f, 390.9f, 355.2f)
                curveTo(389.4f, 360f, 390.8f, 360.4f, 390.8f, 360.4f)
                lineTo(442f, 360.4f)
                curveTo(443.1f, 361.8f, 459.4f, 278.7f, 461.5f, 298.1f)
            }
            path(
                fill = SolidColor(Color(0xFFE9B697)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(553.7f, 243.5f)
                curveTo(556.9f, 243.5f, 559.9f, 242.8f, 562.5f, 241.5f)
                curveTo(564f, 241.9f, 565.6f, 242.1f, 567.2f, 242.1f)
                curveTo(578.8f, 242.1f, 588.2f, 232.7f, 588.2f, 221.1f)
                curveTo(588.2f, 220.6f, 588.2f, 220.1f, 588.1f, 219.6f)
                curveTo(591.7f, 215.8f, 593.9f, 210.8f, 593.9f, 205.2f)
                curveTo(593.9f, 199.6f, 591.7f, 194.5f, 588.1f, 190.7f)
                curveTo(588.2f, 189.8f, 588.3f, 189f, 588.3f, 188.1f)
                curveTo(588.3f, 181.5f, 585.2f, 175.6f, 580.5f, 171.8f)
                curveTo(580.6f, 170.8f, 580.7f, 169.9f, 580.7f, 168.9f)
                curveTo(580.7f, 167.4f, 580.5f, 165.9f, 580.2f, 164.5f)
                curveTo(580.5f, 163.1f, 580.7f, 161.6f, 580.7f, 160.1f)
                curveTo(580.7f, 153.5f, 577.6f, 147.5f, 572.8f, 143.7f)
                curveTo(572.3f, 139f, 570.2f, 134.9f, 567.1f, 131.6f)
                curveTo(565.9f, 121.3f, 557.3f, 113.3f, 546.8f, 113.1f)
                curveTo(542.9f, 108.7f, 537.3f, 105.8f, 530.9f, 105.8f)
                curveTo(528.5f, 105.8f, 526.2f, 106.2f, 524.1f, 107f)
                curveTo(521f, 105.2f, 517.3f, 104.1f, 513.5f, 104.1f)
                curveTo(509.6f, 104.1f, 506f, 105.2f, 502.9f, 107f)
                curveTo(500.8f, 106.3f, 498.5f, 105.9f, 496.2f, 105.9f)
                curveTo(486.7f, 105.9f, 478.7f, 112.2f, 476.1f, 120.8f)
                curveTo(468.2f, 122.5f, 462f, 128.6f, 460.1f, 136.5f)
                curveTo(453.4f, 140f, 448.7f, 147f, 448.7f, 155.1f)
                curveTo(448.7f, 157.1f, 449f, 159f, 449.5f, 160.8f)
                curveTo(447f, 164.3f, 445.5f, 168.5f, 445.5f, 173.1f)
                curveTo(445.5f, 175.2f, 445.8f, 177.3f, 446.4f, 179.2f)
                curveTo(441.5f, 183f, 438.4f, 189f, 438.4f, 195.7f)
                curveTo(438.4f, 200f, 439.7f, 204f, 442f, 207.4f)
                curveTo(441.9f, 210.9f, 442.7f, 214.3f, 444.2f, 217.2f)
                curveTo(435.6f, 223.9f, 432.2f, 237.3f, 442.2f, 249.4f)
                curveTo(450.9f, 256f, 460.6f, 256f, 467.6f, 251.9f)
                curveTo(470.7f, 253.7f, 474.3f, 254.7f, 478.1f, 254.7f)
                curveTo(487.8f, 254.7f, 495.9f, 248.1f, 498.4f, 239.2f)
            }
            path(
                fill = SolidColor(Color(0xFFFFA279)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(479.8f, 202.5f)
                lineTo(515.1f, 247.7f)
                lineTo(533.8f, 217.3f)
                curveTo(533.8f, 217.3f, 539.8f, 210.2f, 544.4f, 202.5f)
                curveTo(549f, 194.7f, 544.4f, 171.8f, 544.4f, 171.8f)
                curveTo(544.4f, 171.8f, 540.2f, 164f, 533.1f, 140.2f)
                curveTo(533.1f, 140.2f, 523.2f, 149.6f, 509.1f, 152.4f)
                curveTo(495f, 155.2f, 475.9f, 190.5f, 479.8f, 202.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFF8B68)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(505f, 230.3f)
                curveTo(507.8f, 231f, 539.7f, 230.9f, 533.8f, 217.3f)
                curveTo(533.8f, 217.3f, 518.6f, 231f, 488.3f, 219f)
                lineTo(505f, 230.3f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0099FF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(488f, 213.1f)
                curveTo(488f, 213.1f, 495.6f, 223.6f, 511f, 231.2f)
                curveTo(511f, 231.2f, 518.4f, 239.7f, 519.7f, 238.8f)
                curveTo(521f, 237.9f, 525.7f, 225.2f, 525.7f, 225.2f)
                lineTo(533.8f, 217.4f)
                lineTo(550.6f, 239f)
                curveTo(550.6f, 239f, 555.2f, 246.5f, 552.9f, 254.5f)
                curveTo(550.6f, 262.4f, 528.7f, 266f, 528.7f, 266f)
                curveTo(528.7f, 266f, 509.3f, 268.6f, 497.3f, 258.6f)
                curveTo(485.3f, 248.5f, 480.7f, 227.9f, 480.7f, 227.9f)
                curveTo(480.7f, 227.9f, 479.1f, 217f, 488f, 213.1f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0070BB)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(502.1f, 232.5f)
                curveTo(502.1f, 232.5f, 514.3f, 238.1f, 517.6f, 240.3f)
                curveTo(521f, 242.4f, 523.8f, 252.1f, 538.4f, 256.9f)
                curveTo(538.5f, 256.9f, 515.7f, 268.7f, 502.1f, 232.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFE8362)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(545.9f, 180.6f)
                curveTo(543.2f, 159.4f, 543.9f, 175.5f, 533f, 138.2f)
                curveTo(518.5f, 150.4f, 491.7f, 151.2f, 482.6f, 184.5f)
                curveTo(484.7f, 183.2f, 493.1f, 168.8f, 508.6f, 160.6f)
                curveTo(520.8f, 154.1f, 521.7f, 158.4f, 530f, 150f)
                curveTo(530f, 150f, 544.4f, 177.4f, 546.3f, 187.2f)
                lineTo(545.9f, 180.6f)
                close()
            }
            path(
                fill = SolidColor(colorOnPrimary),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(188.1f, 98.1f)
                lineTo(188.1f, 74.6f)
                lineTo(203f, 74.6f)
                lineTo(203f, 78f)
                lineTo(191.6f, 78f)
                lineTo(191.6f, 94.7f)
                lineTo(203f, 94.7f)
                lineTo(203f, 98.1f)
                lineTo(188.1f, 98.1f)
                close()
                moveTo(190f, 87.7f)
                lineTo(190f, 84.2f)
                lineTo(202f, 84.2f)
                lineTo(202f, 87.7f)
                lineTo(190f, 87.7f)
                close()
                moveTo(207.1f, 98.1f)
                lineTo(207.1f, 80.8f)
                lineTo(210.4f, 80.8f)
                lineTo(210.4f, 83f)
                lineTo(210.6f, 83f)
                curveTo(211.1f, 82.2f, 211.7f, 81.5f, 212.6f, 81f)
                curveTo(213.5f, 80.5f, 214.5f, 80.2f, 215.7f, 80.2f)
                curveTo(217.7f, 80.2f, 219.2f, 80.8f, 220.2f, 81.9f)
                curveTo(221.2f, 83.1f, 221.7f, 84.7f, 221.7f, 86.7f)
                lineTo(221.7f, 98f)
                lineTo(218.2f, 98f)
                lineTo(218.2f, 87.3f)
                curveTo(218.2f, 86f, 217.9f, 85f, 217.2f, 84.4f)
                curveTo(216.5f, 83.8f, 215.7f, 83.5f, 214.6f, 83.5f)
                curveTo(213.7f, 83.5f, 213f, 83.7f, 212.4f, 84.1f)
                curveTo(211.8f, 84.5f, 211.3f, 85.1f, 211f, 85.8f)
                curveTo(210.7f, 86.5f, 210.5f, 87.3f, 210.5f, 88.2f)
                lineTo(210.5f, 98f)
                lineTo(207.1f, 98.1f)
                close()
                moveTo(233.4f, 105.8f)
                curveTo(232f, 105.8f, 230.7f, 105.6f, 229.6f, 105.1f)
                curveTo(228.5f, 104.6f, 227.7f, 104f, 227f, 103.2f)
                curveTo(226.3f, 102.4f, 225.9f, 101.5f, 225.7f, 100.5f)
                lineTo(229.1f, 99.7f)
                curveTo(229.4f, 100.6f, 229.8f, 101.3f, 230.5f, 101.8f)
                curveTo(231.2f, 102.4f, 232.1f, 102.6f, 233.3f, 102.6f)
                curveTo(234.8f, 102.6f, 236f, 102.1f, 236.8f, 101.2f)
                curveTo(237.6f, 100.3f, 238f, 99f, 238f, 97.3f)
                lineTo(238f, 95.3f)
                lineTo(237.8f, 95.3f)
                curveTo(237.3f, 96.1f, 236.5f, 96.8f, 235.7f, 97.3f)
                curveTo(234.8f, 97.8f, 233.8f, 98f, 232.5f, 98f)
                curveTo(231.2f, 98f, 229.9f, 97.6f, 228.8f, 96.9f)
                curveTo(227.7f, 96.2f, 226.8f, 95.2f, 226.1f, 93.9f)
                curveTo(225.4f, 92.6f, 225.1f, 91f, 225.1f, 89.1f)
                curveTo(225.1f, 87.2f, 225.4f, 85.6f, 226.1f, 84.3f)
                curveTo(226.8f, 83f, 227.7f, 82f, 228.8f, 81.3f)
                curveTo(229.9f, 80.6f, 231.2f, 80.3f, 232.6f, 80.3f)
                curveTo(233.8f, 80.3f, 234.8f, 80.5f, 235.7f, 81f)
                curveTo(236.6f, 81.5f, 237.3f, 82.1f, 237.8f, 82.9f)
                lineTo(238f, 82.9f)
                lineTo(238f, 80.8f)
                lineTo(241.4f, 80.8f)
                lineTo(241.4f, 97.2f)
                curveTo(241.4f, 99f, 241.1f, 100.6f, 240.4f, 101.9f)
                curveTo(239.7f, 103.2f, 238.8f, 104.1f, 237.6f, 104.8f)
                curveTo(236.4f, 105.4f, 235f, 105.8f, 233.4f, 105.8f)
                close()
                moveTo(233.4f, 94.7f)
                curveTo(234.3f, 94.7f, 235f, 94.5f, 235.7f, 94.1f)
                curveTo(236.4f, 93.7f, 237f, 93.1f, 237.4f, 92.2f)
                curveTo(237.8f, 91.4f, 238f, 90.3f, 238f, 89.1f)
                curveTo(238f, 87.8f, 237.8f, 86.8f, 237.4f, 86f)
                curveTo(237f, 85.2f, 236.4f, 84.6f, 235.7f, 84.1f)
                curveTo(235f, 83.7f, 234.2f, 83.5f, 233.4f, 83.5f)
                curveTo(232.6f, 83.5f, 231.8f, 83.7f, 231.1f, 84.1f)
                curveTo(230.4f, 84.5f, 229.8f, 85.1f, 229.4f, 86f)
                curveTo(229f, 86.8f, 228.7f, 87.9f, 228.7f, 89.1f)
                curveTo(228.7f, 90.4f, 228.9f, 91.4f, 229.4f, 92.2f)
                curveTo(229.8f, 93f, 230.4f, 93.7f, 231.1f, 94.1f)
                curveTo(231.8f, 94.5f, 232.5f, 94.7f, 233.4f, 94.7f)
                close()
                moveTo(246.1f, 98.1f)
                lineTo(246.1f, 72.6f)
                lineTo(249.6f, 72.6f)
                lineTo(249.6f, 98.1f)
                lineTo(246.1f, 98.1f)
                close()
                moveTo(256.2f, 77.2f)
                curveTo(255.5f, 77.2f, 255f, 77f, 254.5f, 76.5f)
                curveTo(254f, 76f, 253.8f, 75.5f, 253.8f, 74.8f)
                curveTo(253.8f, 74.1f, 254f, 73.6f, 254.5f, 73.1f)
                curveTo(255f, 72.7f, 255.5f, 72.4f, 256.2f, 72.4f)
                curveTo(256.9f, 72.4f, 257.5f, 72.6f, 257.9f, 73.1f)
                curveTo(258.4f, 73.5f, 258.6f, 74.1f, 258.6f, 74.8f)
                curveTo(258.6f, 75.5f, 258.4f, 76f, 257.9f, 76.5f)
                curveTo(257.4f, 77f, 256.8f, 77.2f, 256.2f, 77.2f)
                close()
                moveTo(254.4f, 98.1f)
                lineTo(254.4f, 80.8f)
                lineTo(258f, 80.8f)
                lineTo(258f, 98.1f)
                lineTo(254.4f, 98.1f)
                close()
                moveTo(268.9f, 98.7f)
                curveTo(267.6f, 98.7f, 266.4f, 98.5f, 265.3f, 98.1f)
                curveTo(264.2f, 97.7f, 263.4f, 97f, 262.7f, 96.2f)
                curveTo(262f, 95.4f, 261.6f, 94.4f, 261.5f, 93.3f)
                lineTo(264.9f, 92.7f)
                curveTo(265.1f, 93.5f, 265.5f, 94.2f, 266.2f, 94.7f)
                curveTo(266.9f, 95.2f, 267.8f, 95.5f, 268.9f, 95.5f)
                curveTo(269.9f, 95.5f, 270.7f, 95.3f, 271.4f, 94.9f)
                curveTo(272.1f, 94.5f, 272.4f, 93.9f, 272.4f, 93.2f)
                curveTo(272.4f, 92.8f, 272.3f, 92.4f, 272f, 92f)
                curveTo(271.7f, 91.6f, 271.4f, 91.4f, 270.9f, 91.1f)
                curveTo(270.4f, 90.9f, 269.8f, 90.7f, 269f, 90.6f)
                lineTo(267f, 90.2f)
                curveTo(266.1f, 90f, 265.2f, 89.7f, 264.5f, 89.3f)
                curveTo(263.8f, 88.9f, 263.2f, 88.3f, 262.7f, 87.6f)
                curveTo(262.3f, 86.9f, 262.1f, 86.1f, 262.1f, 85.2f)
                curveTo(262.1f, 84.2f, 262.4f, 83.3f, 263f, 82.6f)
                curveTo(263.6f, 81.8f, 264.4f, 81.3f, 265.4f, 80.8f)
                curveTo(266.4f, 80.4f, 267.5f, 80.2f, 268.7f, 80.2f)
                curveTo(269.9f, 80.2f, 271f, 80.4f, 271.9f, 80.7f)
                curveTo(272.8f, 81f, 273.6f, 81.6f, 274.2f, 82.3f)
                curveTo(274.8f, 83f, 275.2f, 83.9f, 275.4f, 85f)
                lineTo(272.2f, 85.5f)
                curveTo(272f, 84.7f, 271.6f, 84.1f, 270.9f, 83.8f)
                curveTo(270.3f, 83.4f, 269.5f, 83.3f, 268.7f, 83.3f)
                curveTo(267.8f, 83.3f, 267f, 83.5f, 266.4f, 83.9f)
                curveTo(265.8f, 84.3f, 265.5f, 84.8f, 265.5f, 85.3f)
                curveTo(265.5f, 85.9f, 265.7f, 86.3f, 266.2f, 86.7f)
                curveTo(266.6f, 87.1f, 267.4f, 87.4f, 268.4f, 87.6f)
                lineTo(270.8f, 88.1f)
                curveTo(272.4f, 88.5f, 273.7f, 89f, 274.6f, 89.8f)
                curveTo(275.5f, 90.6f, 275.9f, 91.8f, 275.9f, 93.2f)
                curveTo(275.9f, 94.3f, 275.6f, 95.3f, 275f, 96.1f)
                curveTo(274.4f, 96.9f, 273.5f, 97.6f, 272.5f, 98f)
                curveTo(271.4f, 98.4f, 270.2f, 98.7f, 268.9f, 98.7f)
                close()
                moveTo(279.4f, 98.1f)
                lineTo(279.4f, 72.6f)
                lineTo(282.9f, 72.6f)
                lineTo(282.9f, 80.6f)
                lineTo(282.7f, 83.2f)
                lineTo(282.9f, 83.2f)
                curveTo(283.3f, 82.4f, 284f, 81.7f, 285f, 81.1f)
                curveTo(286f, 80.6f, 287f, 80.3f, 288.2f, 80.3f)
                curveTo(289.6f, 80.3f, 290.7f, 80.6f, 291.6f, 81.1f)
                curveTo(292.5f, 81.6f, 293.2f, 82.4f, 293.7f, 83.4f)
                curveTo(294.2f, 84.4f, 294.4f, 85.5f, 294.4f, 86.9f)
                lineTo(294.4f, 98.1f)
                lineTo(291f, 98.1f)
                lineTo(291f, 87.3f)
                curveTo(291f, 86.5f, 290.8f, 85.8f, 290.5f, 85.3f)
                curveTo(290.2f, 84.8f, 289.8f, 84.3f, 289.2f, 84f)
                curveTo(288.7f, 83.7f, 288f, 83.5f, 287.3f, 83.5f)
                curveTo(286.4f, 83.5f, 285.7f, 83.7f, 285f, 84.2f)
                curveTo(284.3f, 84.6f, 283.8f, 85.3f, 283.5f, 86f)
                curveTo(283.1f, 86.8f, 282.9f, 87.6f, 282.9f, 88.6f)
                lineTo(282.9f, 98.1f)
                lineTo(279.4f, 98.1f)
                close()
                moveTo(310.6f, 98.1f)
                lineTo(305.6f, 80.8f)
                lineTo(309.4f, 80.8f)
                lineTo(312.5f, 93.1f)
                lineTo(312.6f, 93.1f)
                lineTo(316.1f, 80.8f)
                lineTo(319.7f, 80.8f)
                lineTo(323.2f, 93.1f)
                lineTo(323.3f, 93.1f)
                lineTo(326.4f, 80.8f)
                lineTo(330.1f, 80.8f)
                lineTo(325.1f, 98.1f)
                lineTo(321.4f, 98.1f)
                lineTo(317.8f, 85.5f)
                lineTo(317.7f, 85.5f)
                lineTo(314.2f, 98.1f)
                lineTo(310.6f, 98.1f)
                close()
                moveTo(334.7f, 77.2f)
                curveTo(334f, 77.2f, 333.5f, 77f, 333f, 76.5f)
                curveTo(332.5f, 76f, 332.3f, 75.5f, 332.3f, 74.8f)
                curveTo(332.3f, 74.1f, 332.5f, 73.6f, 333f, 73.1f)
                curveTo(333.5f, 72.7f, 334f, 72.4f, 334.7f, 72.4f)
                curveTo(335.4f, 72.4f, 336f, 72.6f, 336.4f, 73.1f)
                curveTo(336.9f, 73.5f, 337.1f, 74.1f, 337.1f, 74.8f)
                curveTo(337.1f, 75.5f, 336.9f, 76f, 336.4f, 76.5f)
                curveTo(336f, 77f, 335.4f, 77.2f, 334.7f, 77.2f)
                close()
                moveTo(333f, 98.1f)
                lineTo(333f, 80.8f)
                lineTo(336.6f, 80.8f)
                lineTo(336.6f, 98.1f)
                lineTo(333f, 98.1f)
                close()
                moveTo(339.8f, 80.8f)
                lineTo(350.1f, 80.8f)
                lineTo(350.1f, 83.9f)
                lineTo(339.8f, 83.9f)
                lineTo(339.8f, 80.8f)
                close()
                moveTo(342.6f, 93.3f)
                lineTo(342.6f, 76.1f)
                lineTo(346.1f, 76.1f)
                lineTo(346.1f, 92.7f)
                curveTo(346.1f, 93.5f, 346.3f, 94.1f, 346.6f, 94.5f)
                curveTo(347f, 94.9f, 347.5f, 95.1f, 348.2f, 95.1f)
                curveTo(348.6f, 95.1f, 348.9f, 95.1f, 349.2f, 95f)
                curveTo(349.5f, 94.9f, 349.8f, 94.8f, 350.1f, 94.6f)
                lineTo(350.1f, 98f)
                curveTo(349.7f, 98.1f, 349.4f, 98.2f, 349f, 98.3f)
                curveTo(348.6f, 98.4f, 348.1f, 98.4f, 347.6f, 98.4f)
                curveTo(346.1f, 98.4f, 344.9f, 97.9f, 344f, 97f)
                curveTo(343.1f, 96.1f, 342.6f, 94.9f, 342.6f, 93.3f)
                close()
                moveTo(354.1f, 98.1f)
                lineTo(354.1f, 72.6f)
                lineTo(357.6f, 72.6f)
                lineTo(357.6f, 80.6f)
                lineTo(357.4f, 83.2f)
                lineTo(357.6f, 83.2f)
                curveTo(358f, 82.4f, 358.7f, 81.7f, 359.7f, 81.1f)
                curveTo(360.7f, 80.6f, 361.7f, 80.3f, 362.9f, 80.3f)
                curveTo(364.3f, 80.3f, 365.4f, 80.6f, 366.3f, 81.1f)
                curveTo(367.2f, 81.6f, 367.9f, 82.4f, 368.4f, 83.4f)
                curveTo(368.9f, 84.4f, 369.1f, 85.5f, 369.1f, 86.9f)
                lineTo(369.1f, 98.1f)
                lineTo(365.6f, 98.1f)
                lineTo(365.6f, 87.3f)
                curveTo(365.6f, 86.5f, 365.4f, 85.8f, 365.1f, 85.3f)
                curveTo(364.8f, 84.8f, 364.4f, 84.3f, 363.8f, 84f)
                curveTo(363.3f, 83.7f, 362.6f, 83.5f, 361.9f, 83.5f)
                curveTo(361f, 83.5f, 360.3f, 83.7f, 359.6f, 84.2f)
                curveTo(358.9f, 84.6f, 358.4f, 85.3f, 358.1f, 86f)
                curveTo(357.7f, 86.8f, 357.5f, 87.6f, 357.5f, 88.6f)
                lineTo(357.5f, 98.1f)
                lineTo(354.1f, 98.1f)
                close()
                moveTo(382.7f, 98.1f)
                lineTo(382.7f, 74.6f)
                lineTo(386.3f, 74.6f)
                lineTo(386.3f, 94.7f)
                lineTo(397.5f, 94.7f)
                lineTo(397.5f, 98.1f)
                lineTo(382.7f, 98.1f)
                close()
                moveTo(402.9f, 77.2f)
                curveTo(402.2f, 77.2f, 401.7f, 77f, 401.2f, 76.5f)
                curveTo(400.7f, 76f, 400.5f, 75.5f, 400.5f, 74.8f)
                curveTo(400.5f, 74.1f, 400.7f, 73.6f, 401.2f, 73.1f)
                curveTo(401.7f, 72.7f, 402.2f, 72.4f, 402.9f, 72.4f)
                curveTo(403.6f, 72.4f, 404.2f, 72.6f, 404.6f, 73.1f)
                curveTo(405.1f, 73.5f, 405.3f, 74.1f, 405.3f, 74.8f)
                curveTo(405.3f, 75.5f, 405.1f, 76f, 404.6f, 76.5f)
                curveTo(404.2f, 77f, 403.6f, 77.2f, 402.9f, 77.2f)
                close()
                moveTo(401.1f, 98.1f)
                lineTo(401.1f, 80.8f)
                lineTo(404.7f, 80.8f)
                lineTo(404.7f, 98.1f)
                lineTo(401.1f, 98.1f)
                close()
                moveTo(416f, 98.7f)
                curveTo(414.5f, 98.7f, 413.2f, 98.3f, 412f, 97.6f)
                curveTo(410.8f, 96.9f, 409.9f, 95.8f, 409.3f, 94.4f)
                curveTo(408.7f, 93f, 408.3f, 91.4f, 408.3f, 89.5f)
                curveTo(408.3f, 87.6f, 408.6f, 86f, 409.3f, 84.6f)
                curveTo(410f, 83.2f, 410.9f, 82.2f, 412f, 81.4f)
                curveTo(413.2f, 80.7f, 414.5f, 80.3f, 415.9f, 80.3f)
                curveTo(416.8f, 80.3f, 417.6f, 80.4f, 418.3f, 80.7f)
                curveTo(419f, 81f, 419.6f, 81.3f, 420.1f, 81.7f)
                curveTo(420.6f, 82.1f, 421f, 82.6f, 421.3f, 83.1f)
                lineTo(421.5f, 83.1f)
                lineTo(421.3f, 80.7f)
                lineTo(421.3f, 72.7f)
                lineTo(424.8f, 72.7f)
                lineTo(424.8f, 98.2f)
                lineTo(421.5f, 98.2f)
                lineTo(421.5f, 96f)
                lineTo(421.3f, 96f)
                curveTo(421f, 96.5f, 420.6f, 96.9f, 420.1f, 97.3f)
                curveTo(419.6f, 97.7f, 419f, 98.1f, 418.3f, 98.3f)
                curveTo(417.7f, 98.5f, 416.9f, 98.7f, 416f, 98.7f)
                close()
                moveTo(416.7f, 95.4f)
                curveTo(417.6f, 95.4f, 418.5f, 95.2f, 419.2f, 94.7f)
                curveTo(419.9f, 94.3f, 420.5f, 93.6f, 420.9f, 92.7f)
                curveTo(421.3f, 91.8f, 421.5f, 90.7f, 421.5f, 89.4f)
                curveTo(421.5f, 88.1f, 421.3f, 87f, 420.9f, 86.1f)
                curveTo(420.5f, 85.2f, 419.9f, 84.6f, 419.2f, 84.1f)
                curveTo(418.5f, 83.7f, 417.6f, 83.4f, 416.7f, 83.4f)
                curveTo(415.8f, 83.4f, 414.9f, 83.6f, 414.2f, 84.1f)
                curveTo(413.5f, 84.5f, 412.9f, 85.2f, 412.5f, 86.1f)
                curveTo(412.1f, 87f, 411.9f, 88.1f, 411.9f, 89.4f)
                curveTo(411.9f, 90.7f, 412.1f, 91.8f, 412.5f, 92.6f)
                curveTo(412.9f, 93.5f, 413.5f, 94.2f, 414.2f, 94.6f)
                curveTo(414.9f, 95.2f, 415.7f, 95.4f, 416.7f, 95.4f)
                close()
                moveTo(431.4f, 77.2f)
                curveTo(430.7f, 77.2f, 430.2f, 77f, 429.7f, 76.5f)
                curveTo(429.2f, 76f, 429f, 75.5f, 429f, 74.8f)
                curveTo(429f, 74.1f, 429.2f, 73.6f, 429.7f, 73.1f)
                curveTo(430.2f, 72.7f, 430.7f, 72.4f, 431.4f, 72.4f)
                curveTo(432.1f, 72.4f, 432.7f, 72.6f, 433.1f, 73.1f)
                curveTo(433.6f, 73.5f, 433.8f, 74.1f, 433.8f, 74.8f)
                curveTo(433.8f, 75.5f, 433.6f, 76f, 433.1f, 76.5f)
                curveTo(432.7f, 77f, 432.1f, 77.2f, 431.4f, 77.2f)
                close()
                moveTo(429.6f, 98.1f)
                lineTo(429.6f, 80.8f)
                lineTo(433.2f, 80.8f)
                lineTo(433.2f, 98.1f)
                lineTo(429.6f, 98.1f)
                close()
                moveTo(443.1f, 98.7f)
                curveTo(441.9f, 98.7f, 440.8f, 98.5f, 439.9f, 98f)
                curveTo(439f, 97.6f, 438.3f, 96.9f, 437.8f, 96.1f)
                curveTo(437.3f, 95.3f, 437f, 94.3f, 437f, 93.2f)
                curveTo(437f, 92f, 437.3f, 91.1f, 437.9f, 90.3f)
                curveTo(438.5f, 89.5f, 439.3f, 88.9f, 440.3f, 88.5f)
                curveTo(441.3f, 88.1f, 442.4f, 87.9f, 443.6f, 87.9f)
                curveTo(444.2f, 87.9f, 444.8f, 87.9f, 445.3f, 88f)
                curveTo(445.8f, 88.1f, 446.4f, 88.1f, 446.9f, 88.2f)
                curveTo(447.4f, 88.3f, 447.9f, 88.4f, 448.4f, 88.6f)
                lineTo(448.4f, 86.9f)
                curveTo(448.4f, 85.8f, 448f, 85f, 447.3f, 84.3f)
                curveTo(446.6f, 83.7f, 445.6f, 83.3f, 444.4f, 83.3f)
                curveTo(443.4f, 83.3f, 442.5f, 83.5f, 441.9f, 84f)
                curveTo(441.2f, 84.5f, 440.7f, 85.1f, 440.5f, 86f)
                lineTo(437.4f, 85.3f)
                curveTo(437.6f, 84.2f, 438.1f, 83.3f, 438.7f, 82.6f)
                curveTo(439.4f, 81.9f, 440.2f, 81.3f, 441.2f, 80.9f)
                curveTo(442.2f, 80.5f, 443.3f, 80.3f, 444.4f, 80.3f)
                curveTo(446.8f, 80.3f, 448.7f, 80.9f, 450f, 82f)
                curveTo(451.3f, 83.2f, 451.9f, 84.8f, 451.9f, 87f)
                lineTo(451.9f, 98.1f)
                lineTo(448.4f, 98.1f)
                lineTo(448.4f, 96.1f)
                lineTo(448.2f, 96.1f)
                curveTo(447.9f, 96.5f, 447.5f, 97f, 447f, 97.3f)
                curveTo(446.5f, 97.7f, 445.9f, 98f, 445.2f, 98.2f)
                curveTo(444.7f, 98.5f, 444f, 98.7f, 443.1f, 98.7f)
                close()
                moveTo(443.8f, 95.8f)
                curveTo(444.8f, 95.8f, 445.6f, 95.6f, 446.3f, 95.2f)
                curveTo(447f, 94.8f, 447.6f, 94.2f, 448f, 93.5f)
                curveTo(448.4f, 92.8f, 448.6f, 92f, 448.6f, 91.2f)
                lineTo(446.5f, 90.6f)
                curveTo(445.8f, 90.5f, 445f, 90.4f, 444.3f, 90.4f)
                curveTo(443.1f, 90.4f, 442.2f, 90.6f, 441.6f, 91.1f)
                curveTo(441f, 91.5f, 440.6f, 92.2f, 440.6f, 93.2f)
                curveTo(440.6f, 94f, 440.9f, 94.6f, 441.5f, 95.1f)
                curveTo(442.1f, 95.6f, 442.9f, 95.8f, 443.8f, 95.8f)
                close()
            }
            path(
                fill = SolidColor(colorOnSecondary),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(271f, 315.9f)
                lineTo(251f, 331.8f)
                curveTo(247.9f, 334.2f, 243.5f, 333.7f, 241f, 330.7f)
                lineTo(225.1f, 310.8f)
                curveTo(222.7f, 307.7f, 223.2f, 303.3f, 226.2f, 300.8f)
                lineTo(246.1f, 284.9f)
                curveTo(249.2f, 282.5f, 253.6f, 283f, 256.1f, 286f)
                lineTo(272f, 305.9f)
                curveTo(274.6f, 309f, 274.1f, 313.5f, 271f, 315.9f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFF8161)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(122.1f, 319.5f)
                lineTo(113.7f, 349.6f)
                lineTo(85.5f, 349.6f)
                lineTo(77.4f, 319.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFCC664D)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(77.4f, 319.5f)
                lineTo(79.9f, 328.9f)
                lineTo(122.1f, 319.5f)
                close()
            }
            path(
                fill = SolidColor(colorPrimaryContainer),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(105.3f, 111.6f)
                lineTo(90.9f, 134.3f)
                curveTo(89.6f, 136.3f, 86.8f, 136.8f, 84.7f, 135.5f)
                lineTo(73.6f, 128.5f)
                curveTo(66.2f, 123.8f, 63f, 114.3f, 67.2f, 107.2f)
                curveTo(71.5f, 99.9f, 81.5f, 97.9f, 89.1f, 102.7f)
                lineTo(91f, 103.9f)
                lineTo(91.6f, 103f)
                curveTo(93.8f, 99.5f, 98.7f, 98.6f, 102.4f, 101f)
                curveTo(106.3f, 103.3f, 107.5f, 108.1f, 105.3f, 111.6f)
                close()
                moveTo(98.5f, 113.7f)
                lineTo(92.7f, 110f)
                lineTo(81.2f, 128.1f)
                lineTo(87f, 131.8f)
                lineTo(98.5f, 113.7f)
                close()
                moveTo(88.8f, 107.5f)
                lineTo(86.9f, 106.3f)
                curveTo(81.5f, 102.9f, 74.6f, 104.2f, 71.4f, 109.2f)
                curveTo(68.2f, 114.2f, 70f, 121f, 75.4f, 124.4f)
                lineTo(77.3f, 125.6f)
                moveTo(95.6f, 105.4f)
                lineTo(95f, 106.3f)
                lineTo(100.8f, 110f)
                lineTo(101.4f, 109.1f)
                curveTo(102.4f, 107.6f, 101.8f, 105.6f, 100.2f, 104.5f)
                curveTo(98.6f, 103.6f, 96.5f, 103.9f, 95.6f, 105.4f)
                close()
                moveTo(57.5f, 118.2f)
                lineTo(75.9f, 89.2f)
                lineTo(72f, 86.7f)
                lineTo(53.6f, 115.7f)
                lineTo(57.5f, 118.2f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF729461)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(92f, 302.4f)
                curveTo(87.8f, 297.2f, 87.2f, 285.7f, 85f, 277.3f)
                curveTo(84f, 273.4f, 81.4f, 270.3f, 78.1f, 268f)
                curveTo(72.3f, 264f, 67.7f, 251.4f, 62.1f, 244.6f)
                curveTo(58.7f, 240.5f, 52.6f, 239.8f, 48.6f, 243.3f)
                curveTo(40f, 250.7f, 62.6f, 259.2f, 63.4f, 272.4f)
                curveTo(57.6f, 286.3f, 71f, 296.3f, 81f, 302.2f)
                curveTo(85.9f, 304.9f, 88.9f, 317.2f, 93.4f, 317.5f)
                curveTo(91.6f, 307.8f, 80.3f, 294.6f, 74.5f, 286.2f)
                curveTo(73.4f, 284.6f, 72.8f, 282.6f, 72.8f, 280.6f)
                curveTo(72.7f, 266.4f, 54.8f, 263.2f, 54.1f, 247.5f)
                curveTo(56f, 254.2f, 73.7f, 270.2f, 74.1f, 279f)
                curveTo(74.2f, 281.3f, 75.1f, 283.4f, 76.4f, 285.3f)
                curveTo(80.9f, 291.5f, 89.4f, 298.7f, 91.2f, 305.4f)
                curveTo(93.1f, 322.8f, 96.8f, 314.7f, 93.6f, 305.7f)
                curveTo(93.3f, 304.4f, 92.7f, 303.3f, 92f, 302.4f)
                close()
                moveTo(93.5f, 317.6f)
                lineTo(93.5f, 317.5f)
                lineTo(93.5f, 317.6f)
                close()
                moveTo(149.9f, 243.8f)
                curveTo(146.3f, 228.4f, 132.2f, 243.4f, 135.6f, 252f)
                curveTo(136.6f, 258.3f, 128.5f, 264.3f, 121.8f, 268.5f)
                curveTo(117.9f, 271f, 115.2f, 275f, 114.5f, 279.6f)
                curveTo(112.6f, 292.1f, 111.2f, 311f, 105.3f, 316.5f)
                curveTo(113f, 321.2f, 123.5f, 294.2f, 130.4f, 289.6f)
                curveTo(146.9f, 278.6f, 132.5f, 276.1f, 145.3f, 258.6f)
                curveTo(149.7f, 252.8f, 150.3f, 247.5f, 149.9f, 243.8f)
                close()
                moveTo(137.7f, 263.5f)
                curveTo(138.8f, 268.5f, 127f, 281f, 127f, 281f)
                curveTo(122.8f, 289.4f, 117.4f, 311.5f, 107.6f, 316.6f)
                curveTo(114.9f, 309.2f, 120.3f, 290.4f, 124.3f, 281.6f)
                curveTo(125.9f, 283.4f, 139.8f, 261.6f, 135.8f, 259.4f)
                curveTo(136.4f, 258.7f, 139.4f, 247.3f, 145.5f, 243.2f)
                curveTo(145.4f, 243.2f, 136.5f, 258.5f, 137.7f, 263.5f)
                close()
                moveTo(108.6f, 226.7f)
                curveTo(108.5f, 221.9f, 108.9f, 215.7f, 109.9f, 209.9f)
                curveTo(108.3f, 199.4f, 94.5f, 185.5f, 89.1f, 197.1f)
                curveTo(87.2f, 201.2f, 87.4f, 206f, 89.4f, 210.1f)
                curveTo(91.8f, 214.9f, 94.9f, 222.1f, 93.3f, 230.8f)
                curveTo(73.5f, 277.2f, 97.5f, 264.9f, 100.3f, 315.1f)
                curveTo(106.5f, 288.1f, 93.5f, 271.4f, 102.9f, 243.6f)
                curveTo(103.3f, 242.3f, 103.4f, 240.9f, 103.3f, 239.6f)
                curveTo(100.6f, 219.5f, 91.5f, 221.6f, 100.1f, 198.9f)
                curveTo(97.5f, 206.6f, 108.8f, 244.3f, 103.4f, 256.4f)
                curveTo(102.4f, 258.6f, 101.9f, 261f, 102.1f, 263.4f)
                curveTo(103.5f, 278.7f, 110f, 302.9f, 101f, 315.7f)
                curveTo(108.9f, 307.5f, 119.3f, 251.1f, 111.2f, 236.7f)
                curveTo(109.4f, 233.6f, 108.7f, 230.2f, 108.6f, 226.7f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFF8161)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(100.1f, 315.6f)
                lineTo(100.3f, 315.2f)
                lineTo(100.2f, 315.1f)
                lineTo(100.1f, 315.6f)
                close()
            }
            path(
                fill = SolidColor(colorSecondaryContainer),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(485.9f, 72.5f)
                curveTo(481.3f, 67.2f, 474.9f, 75f, 470.2f, 72.9f)
                curveTo(468.6f, 72.3f, 466.3f, 71.4f, 463.6f, 70.8f)
                curveTo(462.9f, 70.7f, 462.3f, 70.6f, 461.6f, 70.7f)
                curveTo(456.5f, 71.5f, 454.9f, 79f, 457.3f, 83.2f)
                curveTo(460.3f, 87.7f, 464.4f, 100.8f, 470.3f, 102.4f)
                curveTo(471.5f, 102.9f, 472.9f, 102.9f, 474.1f, 102.4f)
                curveTo(479.9f, 100.9f, 484.2f, 87.6f, 487.1f, 83.2f)
                curveTo(489f, 79.8f, 488.6f, 75.4f, 485.9f, 72.5f)
                close()
            }
        }.build()
    }
}

