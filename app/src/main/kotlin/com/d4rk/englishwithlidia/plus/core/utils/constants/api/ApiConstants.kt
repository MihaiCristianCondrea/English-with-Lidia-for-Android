package com.d4rk.englishwithlidia.plus.core.utils.constants.api

object ApiConstants {
    const val DEFAULT_BASE_REPOSITORY_URL =
        "https://raw.githubusercontent.com/D4rK7355608/com.d4rk.apis/refs/heads/main/English%20with%20Lidia"
    const val DEFAULT_LANGUAGE_CODE = "ro"

    fun resolveRepositoryBaseUrl(candidate: String): String {
        val trimmed = candidate.trim()
        if (trimmed.isEmpty()) {
            return DEFAULT_BASE_REPOSITORY_URL
        }

        val normalized = trimmed.trimEnd('/')
        return if (normalized.isEmpty()) {
            DEFAULT_BASE_REPOSITORY_URL
        } else {
            normalized.replace(" ", "%20")
        }
    }
}
