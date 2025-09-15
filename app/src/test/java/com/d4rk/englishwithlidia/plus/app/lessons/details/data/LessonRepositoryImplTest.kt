package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import android.net.Uri
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.mockk.*

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LessonRepositoryImplTest {

    @Test
    fun `getLesson returns content with cached audio uri and resolves only non-empty urls`() = runTest {
        val json = """
            {
              "data": [
                {
                  "lesson_title": "Lesson 1",
                  "lesson_content": [
                    {
                      "content_id": "1",
                      "content_audio_url": "https://example.com/audio1.mp3"
                    },
                    {
                      "content_id": "2",
                      "content_audio_url": ""
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val client = HttpClient(MockEngine { respond(json) })
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val audioCache: AudioCacheManager = mockk()
        var resolveCalls = 0
        coEvery { audioCache.resolve(any(), any()) } coAnswers {
            resolveCalls++
            Uri.parse("cached://audio1")
        }

        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache)

        val result = repository.getLesson("1")

        assertEquals("Lesson 1", result.lessonTitle)
        assertEquals(2, result.lessonContent.size)
        assertEquals("cached://audio1", result.lessonContent[0].contentAudioUrl)
        assertTrue(result.lessonContent[1].contentAudioUrl.isEmpty())
        assertEquals(1, resolveCalls)
    }

    @Test
    fun `getLesson returns empty screen when client throws`() = runTest {
        val client = HttpClient(MockEngine { throw RuntimeException("boom") })
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val audioCache: AudioCacheManager = mockk(relaxed = true)
        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache)

        val result = repository.getLesson("1")

        assertEquals(UiLessonScreen(), result)
    }
}

