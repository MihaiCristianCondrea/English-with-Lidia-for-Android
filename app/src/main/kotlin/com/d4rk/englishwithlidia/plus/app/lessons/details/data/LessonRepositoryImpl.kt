package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class LessonRepositoryImpl(
    private val client: HttpClient,
    private val dispatchers: DispatcherProvider,
    private val mapper: LessonMapper,
    private val audioCache: AudioCacheManager,
    private val jsonParser: Json,
) : LessonRepository {

    private val baseUrl: String by lazy {
        val environment = if (BuildConfig.DEBUG) "debug" else "release"
        "${ApiConstants.BASE_REPOSITORY_URL}/$environment/ro/lessons"
    }

    override fun getLesson(lessonId: String): Flow<Lesson?> {
        return flow {
            val url = "$baseUrl/api_get_$lessonId.json"
            emit(client.get(url).bodyAsText())
        }
            .map { jsonString ->
                jsonString.takeUnless { it.isBlank() }
                    ?.let { jsonParser.decodeFromString<ApiLessonResponse>(it) }
                    ?.let(mapper::map)
                    .orEmpty()
            }
            .map { lessons ->
                lessons.map { lesson ->
                    val cachedContent = lesson.lessonContent.map { content ->
                        val cachedUrl = content.contentAudioUrl.takeUnless { it.isBlank() }
                            ?.let { audioCache.resolve(content.contentId, it).toString() }
                            ?: content.contentAudioUrl

                        content.copy(contentAudioUrl = cachedUrl)
                    }

                    lesson.copy(lessonContent = cachedContent)
                }
            }
            .map { lessons -> lessons.firstOrNull() }
            .flowOn(dispatchers.io)
            .catch { emit(null) }
    }
}
