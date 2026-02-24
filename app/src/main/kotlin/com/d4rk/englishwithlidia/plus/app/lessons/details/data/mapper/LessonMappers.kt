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

package com.d4rk.englishwithlidia.plus.app.lessons.details.data.mapper

import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonContentDto
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonDto
import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonResponseDto
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.LessonContent

internal fun LessonResponseDto.firstLessonOrNull(): Lesson? =
    data.firstOrNull()?.toDomain()

internal fun LessonDto.toDomain(): Lesson =
    Lesson(
        lessonTitle = lessonTitle,
        writer = writer,
        lessonContent = lessonContent.map(LessonContentDto::toDomain),
    )

internal fun LessonContentDto.toDomain(): LessonContent =
    LessonContent(
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
