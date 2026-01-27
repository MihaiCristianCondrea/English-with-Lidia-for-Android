package com.d4rk.englishwithlidia.plus.core.utils.constants.api

import com.d4rk.android.libs.apptoolkit.core.utils.constants.github.GithubConstants

object EnglishWithLidiaApiHost {
    const val API_REPO: String = "com.d4rk.apis"
    const val API_BASE_PATH: String = "api/english_with_lidia"
}

object EnglishWithLidiaApiVersions {
    const val V1: String = "v1"
}

object EnglishWithLidiaApiLanguages {
    const val RO: String = "ro"
}

object EnglishWithLidiaApiPaths {
    const val HOME_LESSONS: String = "home/api_get_lessons.json"

    const val LESSONS_DIR: String = "lessons"
    const val LESSON_DETAILS_PREFIX: String = "api_get_"
    const val JSON_SUFFIX: String = ".json"
}

object EnglishWithLidiaApiConstants {
    const val BASE_REPOSITORY_URL: String =
        "${GithubConstants.GITHUB_PAGES}/${EnglishWithLidiaApiHost.API_REPO}/${EnglishWithLidiaApiHost.API_BASE_PATH}/${EnglishWithLidiaApiVersions.V1}"
}

object EnglishWithLidiaApiEndpoints {

    fun homeLessons(environment: String, language: String = EnglishWithLidiaApiLanguages.RO): String {
        return "${EnglishWithLidiaApiConstants.BASE_REPOSITORY_URL}/$environment/$language/${EnglishWithLidiaApiPaths.HOME_LESSONS}"
    }

    fun lessonDetails(
        environment: String,
        lessonId: String,
        language: String = EnglishWithLidiaApiLanguages.RO,
    ): String {
        return buildString {
            append(EnglishWithLidiaApiConstants.BASE_REPOSITORY_URL)
            append("/")
            append(environment)
            append("/")
            append(language)
            append("/")
            append(EnglishWithLidiaApiPaths.LESSONS_DIR)
            append("/")
            append(EnglishWithLidiaApiPaths.LESSON_DETAILS_PREFIX)
            append(lessonId)
            append(EnglishWithLidiaApiPaths.JSON_SUFFIX)
        }
    }
}
