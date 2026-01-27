package com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote

import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.model.HomeLessonsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

// TODO: Review
fun interface HomeRemoteDataSource {
    suspend fun fetchHomeLessons(urlString: String): HomeLessonsResponseDto
}

class KtorHomeRemoteDataSource(
    private val client: HttpClient,
    private val jsonParser: Json,
) : HomeRemoteDataSource {

    override suspend fun fetchHomeLessons(urlString: String): HomeLessonsResponseDto {
        val jsonString = client.get(urlString).bodyAsText()

        return jsonString
            .takeUnless { it.isBlank() }
            ?.let { jsonParser.decodeFromString<HomeLessonsResponseDto>(it) }
            ?: HomeLessonsResponseDto()
    }
}
