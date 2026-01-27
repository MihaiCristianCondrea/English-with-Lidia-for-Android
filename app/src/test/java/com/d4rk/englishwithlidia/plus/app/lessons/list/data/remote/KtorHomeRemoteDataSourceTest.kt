/*
package com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote

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
class KtorHomeRemoteDataSourceTest {

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun `fetchHomeLessons parses json response`() = runTest {
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
        val dataSource = KtorHomeRemoteDataSource(
            client = client,
            endpointProvider = HomeEndpointProvider { endpoint },
            jsonParser = jsonParser,
        )

        val response = dataSource.fetchHomeLessons()

        requireNotNull(response)
        assertEquals(1, response.data.size)
        assertEquals("1", response.data.first().lessonId)
        assertEquals("Lesson 1", response.data.first().lessonTitle)
    }

    @Test
    fun `fetchHomeLessons returns null for blank response`() = runTest {
        val endpoint = "https://example.com/home"
        val client = HttpClient(MockEngine { respond("") })
        val dataSource = KtorHomeRemoteDataSource(
            client = client,
            endpointProvider = HomeEndpointProvider { endpoint },
            jsonParser = jsonParser,
        )

        val response = dataSource.fetchHomeLessons()

        assertNull(response)
    }

    @Test
    fun `fetchHomeLessons propagates network errors`() = runTest {
        val endpoint = "https://example.com/home"
        val client = HttpClient(MockEngine { throw IllegalStateException("boom") })
        val dataSource = KtorHomeRemoteDataSource(
            client = client,
            endpointProvider = HomeEndpointProvider { endpoint },
            jsonParser = jsonParser,
        )

        try {
            dataSource.fetchHomeLessons()
            fail("Expected exception to be thrown")
        } catch (expected: IllegalStateException) {
            assertEquals("boom", expected.message)
        }
    }
}
*/
