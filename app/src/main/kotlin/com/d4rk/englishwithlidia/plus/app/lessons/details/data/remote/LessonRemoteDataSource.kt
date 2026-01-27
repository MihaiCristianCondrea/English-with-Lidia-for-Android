package com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote

import com.d4rk.englishwithlidia.plus.app.lessons.details.data.remote.model.LessonResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

// TODO: problem... review.. this should not be like that. we can make it directly one class
fun interface LessonRemoteDataSource {
    suspend fun fetchLesson(urlString: String): LessonResponseDto
}

class KtorLessonRemoteDataSource(
    private val client: HttpClient,
    private val jsonParser: Json,
) : LessonRemoteDataSource {

    override suspend fun fetchLesson(urlString: String): LessonResponseDto {
        val jsonString = client.get(urlString).bodyAsText()
        return jsonString
            .takeUnless { it.isBlank() }
            ?.let { jsonParser.decodeFromString<LessonResponseDto>(it) }
            ?: LessonResponseDto()
    }
}
