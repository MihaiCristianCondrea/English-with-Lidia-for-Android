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

package com.d4rk.englishwithlidia.plus.core.domain.model.network

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.Error
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.Errors

/**
 * App-specific error surface.
 *
 * The app can emit its own errors (e.g., developer apps list) while still allowing shared
 * toolkit errors to flow through unchanged via [Common]. This keeps the app extensible without
 * duplicating the shared error taxonomy.
 */

/**
 * App-specific error surface.
 *
 * - [Common] wraps AppToolkit [Errors] unchanged.
 * - [UseCase] covers app/feature-specific situations where toolkit taxonomy is too generic.
 */
sealed interface AppErrors : Error {

    data class Common(val value: Errors) : AppErrors

    enum class UseCase : AppErrors {
        // Lessons list
        FAILED_TO_LOAD_LESSONS,
        FAILED_TO_PARSE_LESSONS,

        // Lesson details
        FAILED_TO_LOAD_LESSON,
        INVALID_LESSON_ID,
        LESSON_NOT_FOUND,
        LESSON_EMPTY,
        INVALID_LESSON_RESPONSE,
        FAILED_TO_PARSE_LESSON,

        // Audio
        AUDIO_PLAYBACK,
        AUDIO_NOT_AVAILABLE,
        FAILED_TO_CACHE_AUDIO,
    }
}