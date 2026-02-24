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

package com.d4rk.englishwithlidia.plus.app.lessons.details.ui.mappers

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.ui.state.UiLessonScreen
internal fun Lesson.toUiModel(): UiLessonScreen =
    UiLessonScreen(
        lessonTitle = lessonTitle,
        writer = writer,
        lessonContent = lessonContent.map(LessonContent::toUiModel),
    )

internal fun LessonContent.toUiModel(): UiLessonContent =
    UiLessonContent(
        contentId = contentId,
        contentType = contentType,
        contentText = contentText,
        contentImageUrl = contentImageUrl,
        contentAudioUrl = contentAudioUrl,
        contentThumbnailUrl = contentThumbnailUrl,
        contentTitle = contentTitle,
        contentArtist = contentArtist,
        contentAlbumTitle = contentAlbumTitle,
        contentGenre = contentGenre,
        contentDescription = contentDescription,
        contentReleaseYear = contentReleaseYear,
        writer = writer,
    )
