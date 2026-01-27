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