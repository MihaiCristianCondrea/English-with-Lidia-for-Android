/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
