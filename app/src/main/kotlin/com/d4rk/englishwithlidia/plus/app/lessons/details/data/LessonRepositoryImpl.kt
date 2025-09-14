package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class LessonRepositoryImpl(
    private val client: HttpClient,
    private val dispatchers: DispatcherProvider,
    private val mapper: LessonMapper,
    private val jsonParser: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    },
) : LessonRepository {

    private val baseUrl = BuildConfig.DEBUG.let { isDebug ->
        val environment = if (isDebug) "debug" else "release"
        "${ApiConstants.BASE_REPOSITORY_URL}/$environment/ro/lessons"
    }

    override suspend fun getLesson(lessonId: String): UiLessonScreen =
        withContext(dispatchers.io) {
            val url = "$baseUrl/api_get_$lessonId.json"
            val jsonString = client.get(url).bodyAsText()

            val lessons = jsonString.takeUnless { it.isBlank() }
                ?.let { jsonParser.decodeFromString<ApiLessonResponse>(it) }
                ?.takeIf { it.data.isNotEmpty() }
                ?.let { mapper.map(it) }
                ?: emptyList()
            lessons.firstOrNull() ?: UiLessonScreen()
        }
}
