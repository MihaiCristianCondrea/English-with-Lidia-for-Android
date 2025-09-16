package com.d4rk.englishwithlidia.plus.app.lessons.list.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiHomeLessons
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiHomeResponse
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryImplTest {

    @Test
    fun `getHomeLessons emits mapped HomeScreen when response is valid`() = runTest {
        mockkStatic("io.ktor.client.statement.HttpResponseKt")
        try {
            val httpClient = mockk<HttpClient>()
            val httpResponse = mockk<HttpResponse>()
            val mapper = mockk<HomeMapper>()
            val dispatchers: DispatcherProvider = mockk {
                every { io } returns UnconfinedTestDispatcher(testScheduler)
            }

            val json = """
                {
                  "data": [
                    {
                      "lesson_id": "1",
                      "lesson_title": "Lesson 1",
                      "lesson_type": "video",
                      "lesson_thumbnail_image_url": "url1",
                      "lesson_deep_link_path": "path1"
                    }
                  ]
                }
            """.trimIndent()

            val expectedApiResponse = ApiHomeResponse(
                data = listOf(
                    ApiHomeLessons(
                        lessonId = "1",
                        lessonTitle = "Lesson 1",
                        lessonType = "video",
                        lessonThumbnailImageUrl = "url1",
                        lessonDeepLinkPath = "path1",
                    )
                )
            )
            val expectedHomeScreen = HomeScreen(
                lessons = listOf(
                    HomeLesson(
                        lessonId = "1",
                        lessonTitle = "Lesson 1",
                        lessonType = "video",
                        lessonThumbnailImageUrl = "url1",
                        lessonDeepLinkPath = "path1",
                    )
                )
            )

            val capturedResponse = slot<ApiHomeResponse>()

            coEvery { httpClient.get(any<String>()) } returns httpResponse
            coEvery { httpResponse.bodyAsText() } returns json
            every { mapper.map(capture(capturedResponse)) } returns expectedHomeScreen

            val repository = HomeRepositoryImpl(httpClient, dispatchers, mapper)

            val result = repository.getHomeLessons().first()

            assertEquals(expectedHomeScreen, result)
            assertEquals(expectedApiResponse, capturedResponse.captured)
            verify(exactly = 1) { mapper.map(any()) }
        } finally {
            unmockkStatic("io.ktor.client.statement.HttpResponseKt")
        }
    }

    @Test
    fun `getHomeLessons emits empty HomeScreen when parsing fails`() = runTest {
        mockkStatic("io.ktor.client.statement.HttpResponseKt")
        try {
            val httpClient = mockk<HttpClient>()
            val httpResponse = mockk<HttpResponse>()
            val mapper = mockk<HomeMapper>()
            val dispatchers: DispatcherProvider = mockk {
                every { io } returns UnconfinedTestDispatcher(testScheduler)
            }

            coEvery { httpClient.get(any<String>()) } returns httpResponse
            coEvery { httpResponse.bodyAsText() } returns "not json"

            val repository = HomeRepositoryImpl(httpClient, dispatchers, mapper)

            val result = repository.getHomeLessons().first()

            assertEquals(HomeScreen(), result)
            verify(exactly = 0) { mapper.map(any()) }
        } finally {
            unmockkStatic("io.ktor.client.statement.HttpResponseKt")
        }
    }

    @Test
    fun `getHomeLessons emits empty HomeScreen when network call fails`() = runTest {
        val httpClient = mockk<HttpClient>()
        val mapper = mockk<HomeMapper>()
        val dispatchers: DispatcherProvider = mockk {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }

        coEvery { httpClient.get(any<String>()) } throws RuntimeException("boom")

        val repository = HomeRepositoryImpl(httpClient, dispatchers, mapper)

        val result = repository.getHomeLessons().first()

        assertEquals(HomeScreen(), result)
        verify(exactly = 0) { mapper.map(any()) }
    }

    @Test
    fun `getHomeLessons rethrows CancellationException`() = runTest {
        val httpClient = mockk<HttpClient>()
        val mapper = mockk<HomeMapper>()
        val dispatchers: DispatcherProvider = mockk {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }

        coEvery { httpClient.get(any<String>()) } throws CancellationException("cancelled")

        val repository = HomeRepositoryImpl(httpClient, dispatchers, mapper)

        try {
            repository.getHomeLessons().first()
            fail("Expected CancellationException to be thrown")
        } catch (exception: CancellationException) {
            verify(exactly = 0) { mapper.map(any()) }
        }
    }
}
