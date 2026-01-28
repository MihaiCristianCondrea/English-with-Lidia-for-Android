package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote

import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.model.ListingLessonsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

fun interface ListingDataSource {
    suspend fun fetchListingLessons(urlString: String): ListingLessonsResponseDto
}

class KtorListingDataSource(
    private val client: HttpClient,
    private val jsonParser: Json,
) : ListingDataSource {

    override suspend fun fetchListingLessons(urlString: String): ListingLessonsResponseDto {
        val jsonString = client.get(urlString).bodyAsText()

        return jsonString
            .takeUnless { it.isBlank() }
            ?.let { jsonParser.decodeFromString<ListingLessonsResponseDto>(it) }
            ?: ListingLessonsResponseDto()
    }
}
