package com.d4rk.englishwithlidia.plus.app.lessons.list.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryImplTest {

    @Test
    fun `getHomeLessons emits mapped HomeScreen for valid json`() = runTest {
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

        val client = HttpClient(MockEngine { respond(json) })
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = HomeRepositoryImpl(client, dispatchers, HomeMapper())

        val result = repository.getHomeLessons().first()

        val expected = HomeScreen(
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
        assertEquals(expected, result)
    }

    @Test
    fun `getHomeLessons emits empty HomeScreen for blank response`() = runTest {
        val client = HttpClient(MockEngine { respond("") })
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = HomeRepositoryImpl(client, dispatchers, HomeMapper())

        val result = repository.getHomeLessons().first()

        assertTrue(result.lessons.isEmpty())
    }

    @Test
    fun `getHomeLessons emits empty HomeScreen when client throws exception`() = runTest {
        val client = HttpClient(MockEngine { throw RuntimeException("boom") })
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = HomeRepositoryImpl(client, dispatchers, HomeMapper())

        val result = repository.getHomeLessons().first()

        assertTrue(result.lessons.isEmpty())
    }
}
