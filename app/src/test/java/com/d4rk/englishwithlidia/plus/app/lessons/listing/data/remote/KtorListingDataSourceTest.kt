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

/*
package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KtorListingRemoteDataSourceTest {

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun `fetchListingLessons parses json response`() = runTest {
        val endpoint = "https://example.com/home"
        val client = HttpClient(MockEngine { request ->
            assertEquals(endpoint, request.url.toString())
            respond(
                content = """
                    {
                      "data": [
                        {
                          "lesson_id": "1",
                          "lesson_title": "Lesson 1"
                        }
                      ]
                    }
                """.trimIndent()
            )
        })
        val dataSource = KtorListingRemoteDataSource(
            client = client,
            endpointProvider = ListingEndpointProvider { endpoint },
            jsonParser = jsonParser,
        )

        val response = dataSource.fetchListingLessons()

        requireNotNull(response)
        assertEquals(1, response.data.size)
        assertEquals("1", response.data.first().lessonId)
        assertEquals("Lesson 1", response.data.first().lessonTitle)
    }

    @Test
    fun `fetchListingLessons returns null for blank response`() = runTest {
        val endpoint = "https://example.com/home"
        val client = HttpClient(MockEngine { respond("") })
        val dataSource = KtorListingRemoteDataSource(
            client = client,
            endpointProvider = ListingEndpointProvider { endpoint },
            jsonParser = jsonParser,
        )

        val response = dataSource.fetchListingLessons()

        assertNull(response)
    }

    @Test
    fun `fetchListingLessons propagates network errors`() = runTest {
        val endpoint = "https://example.com/home"
        val client = HttpClient(MockEngine { throw IllegalStateException("boom") })
        val dataSource = KtorListingRemoteDataSource(
            client = client,
            endpointProvider = ListingEndpointProvider { endpoint },
            jsonParser = jsonParser,
        )

        try {
            dataSource.fetchListingLessons()
            fail("Expected exception to be thrown")
        } catch (expected: IllegalStateException) {
            assertEquals("boom", expected.message)
        }
    }
}
*/
