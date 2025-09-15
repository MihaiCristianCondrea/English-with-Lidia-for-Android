package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
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
    private val audioCache: AudioCacheManager,
    baseRepositoryUrl: String,
    private val jsonParser: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    },
) : LessonRepository {

    private val repositoryBaseUrl = ApiConstants.resolveRepositoryBaseUrl(baseRepositoryUrl)
    private val environmentSegment: String
        get() = if (BuildConfig.DEBUG) "debug" else "release"

    private val lessonsBaseUrl: String
        get() = listOf(
            repositoryBaseUrl,
            environmentSegment,
            ApiConstants.DEFAULT_LANGUAGE_CODE,
            "lessons",
        ).joinToString(separator = "/")

    override suspend fun getLesson(lessonId: String): UiLessonScreen =
        withContext(dispatchers.io) {
            val slug = lessonId.trim()
            if (slug.isEmpty()) {
                return@withContext UiLessonScreen()
            }

            val url = listOf(lessonsBaseUrl, "api_get_${slug}.json").joinToString(separator = "/")
            val jsonString = client.get(url).bodyAsText()

            val lessons = jsonString.takeUnless { it.isBlank() }
                ?.let { jsonParser.decodeFromString<ApiLessonResponse>(it) }
                ?.takeIf { it.data.isNotEmpty() }
                ?.let { mapper.map(it) }
                ?: emptyList()

            val cachedLessons = lessons.map { lesson ->
                val updatedContent = mutableListOf<UiLessonContent>()
                for (content in lesson.lessonContent) {
                    val audioUrl = if (content.contentAudioUrl.isNotBlank()) {
                        audioCache.resolve(content.contentId, content.contentAudioUrl).toString()
                    } else content.contentAudioUrl
                    updatedContent += content.copy(contentAudioUrl = audioUrl)
                }
                lesson.copy(lessonContent = updatedContent)
            }

            cachedLessons.firstOrNull() ?: UiLessonScreen()
        }
}
