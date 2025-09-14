package com.d4rk.englishwithlidia.plus.app.lessons.list.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiHomeResponse
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class HomeRepositoryImpl(
    private val client: HttpClient,
    private val dispatchers: DispatcherProvider,
) : HomeRepository {

    private val baseUrl = BuildConfig.DEBUG.let { isDebug ->
        val environment = if (isDebug) "debug" else "release"
        "${ApiConstants.BASE_REPOSITORY_URL}/$environment/ro/home/api_get_lessons.json"
    }

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun getHomeLessons(): UiHomeScreen = withContext(dispatchers.io) {
        runCatching {
            val jsonString = client.get(baseUrl).bodyAsText()
            val lessons = jsonString.takeUnless { it.isBlank() }
                ?.let { jsonParser.decodeFromString<ApiHomeResponse>(it) }
                ?.takeIf { it.data.isNotEmpty() }?.data?.map { networkLesson ->
                    UiHomeLesson(
                        lessonId = networkLesson.lessonId,
                        lessonTitle = networkLesson.lessonTitle,
                        lessonType = networkLesson.lessonType,
                        lessonThumbnailImageUrl = networkLesson.lessonThumbnailImageUrl,
                        lessonDeepLinkPath = networkLesson.lessonDeepLinkPath,
                    )
                } ?: emptyList()

            UiHomeScreen(lessons = lessons)
        }.getOrElse { throwable ->
            if (throwable is CancellationException) throw throwable
            UiHomeScreen()
        }
    }
}
