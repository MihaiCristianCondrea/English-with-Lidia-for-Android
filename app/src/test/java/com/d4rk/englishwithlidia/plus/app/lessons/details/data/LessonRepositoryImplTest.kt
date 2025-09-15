package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import android.net.Uri
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.headersOf
import io.mockk.any
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

class LessonRepositoryImplTest {

    @Test
    fun `getLesson rewrites audio urls using cache`() = runTest {
        val json = """
            {
              "data": [
                {
                  "lesson_title": "Title",
                  "lesson_content": [
                    {
                      "content_id": "1",
                      "content_type": "audio",
                      "content_text": "t1",
                      "content_audio_url": "http://example.com/a1.mp3"
                    },
                    {
                      "content_id": "2",
                      "content_type": "audio",
                      "content_text": "t2",
                      "content_audio_url": "http://example.com/a2.mp3"
                    }
                  ]
                }
              ]
            }
        """.trimIndent()
        val client = HttpClient(MockEngine { respond(json, headers = headersOf("Content-Type", "application/json")) })

        val audioCache = mockk<AudioCacheManager>()
        coEvery { audioCache.resolve("1", "http://example.com/a1.mp3") } returns Uri.parse("cached://1")
        coEvery { audioCache.resolve("2", "http://example.com/a2.mp3") } returns Uri.parse("cached://2")

        val dispatcher = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        val dispatchers = mockk<DispatcherProvider> { every { io } returns dispatcher }

        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache)

        val result = repository.getLesson("id")

        coVerify(exactly = 1) { audioCache.resolve("1", "http://example.com/a1.mp3") }
        coVerify(exactly = 1) { audioCache.resolve("2", "http://example.com/a2.mp3") }
        assertEquals(listOf("cached://1", "cached://2"), result.lessonContent.map { it.contentAudioUrl })
    }

    @Test
    fun `getLesson returns empty screen for blank response`() = runTest {
        val client = HttpClient(MockEngine { respond("") })

        val audioCache = mockk<AudioCacheManager>(relaxed = true)

        val dispatcher = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        val dispatchers = mockk<DispatcherProvider> { every { io } returns dispatcher }

        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache)

        val result = repository.getLesson("id")

        assertEquals(UiLessonScreen(), result)
        coVerify(exactly = 0) { audioCache.resolve(any(), any()) }
    }
}

