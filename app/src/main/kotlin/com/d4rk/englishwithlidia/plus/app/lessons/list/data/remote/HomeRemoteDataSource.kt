package com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote

import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiHomeResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Abstraction for fetching the remote home lessons payload.
 */
interface HomeRemoteDataSource {
    suspend fun fetchHomeLessons(): ApiHomeResponse?
}

/**
 * Ktor backed implementation that downloads and parses the home lessons payload.
 */
class KtorHomeRemoteDataSource(
    private val client: HttpClient,
    private val endpointProvider: HomeEndpointProvider,
    private val jsonParser: Json,
) : HomeRemoteDataSource {

    override suspend fun fetchHomeLessons(): ApiHomeResponse? {
        val jsonString = client.get(endpointProvider.lessons()).bodyAsText()
        return jsonString.takeUnless { it.isBlank() }
            ?.let { jsonParser.decodeFromString<ApiHomeResponse>(it) }
    }
}
