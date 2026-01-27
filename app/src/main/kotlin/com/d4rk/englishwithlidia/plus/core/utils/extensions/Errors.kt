package com.d4rk.englishwithlidia.plus.core.utils.extensions

import com.d4rk.android.libs.apptoolkit.core.utils.extensions.errors.asUiText
import com.d4rk.android.libs.apptoolkit.core.utils.platform.UiTextHelper
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors

fun AppErrors.toErrorMessage(): UiTextHelper = when (this) {
    is AppErrors.Common -> value.asUiText()

    // Lessons list
    AppErrors.UseCase.FAILED_TO_LOAD_LESSONS -> UiTextHelper.StringResource(R.string.error_failed_to_load_lessons)
    AppErrors.UseCase.FAILED_TO_PARSE_LESSONS -> UiTextHelper.StringResource(R.string.error_failed_to_parse_lessons)
    AppErrors.UseCase.FAILED_TO_LOAD_LESSON -> UiTextHelper.StringResource(R.string.error_failed_to_load_lesson)
    AppErrors.UseCase.INVALID_LESSON_ID -> UiTextHelper.StringResource(R.string.error_invalid_lesson_id)
    AppErrors.UseCase.LESSON_NOT_FOUND -> UiTextHelper.StringResource(R.string.error_lesson_not_found)
    AppErrors.UseCase.LESSON_EMPTY -> UiTextHelper.StringResource(R.string.error_lesson_empty)
    AppErrors.UseCase.INVALID_LESSON_RESPONSE -> UiTextHelper.StringResource(R.string.error_invalid_lesson_response)
    AppErrors.UseCase.FAILED_TO_PARSE_LESSON -> UiTextHelper.StringResource(R.string.error_failed_to_parse_lesson)

    // Audio
    AppErrors.UseCase.AUDIO_PLAYBACK -> UiTextHelper.StringResource(R.string.error_audio_playback)
    AppErrors.UseCase.AUDIO_NOT_AVAILABLE -> UiTextHelper.StringResource(R.string.error_audio_not_available)
    AppErrors.UseCase.FAILED_TO_CACHE_AUDIO -> UiTextHelper.StringResource(R.string.error_failed_to_cache_audio)
}