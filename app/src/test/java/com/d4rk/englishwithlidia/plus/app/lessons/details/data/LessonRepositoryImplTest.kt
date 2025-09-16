package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.mockk.any
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LessonRepositoryImplTest {

    @Test
    fun `getLesson returns fallback audio when cache resolve fails`() = runTest {
        val json = """
            {
              "data": [
                {
                  "lesson_title": "Lesson 1",
                  "lesson_content": [
                    {
                      "content_id": "content1",
                      "content_type": "audio",
                      "content_text": "text",
                      "content_audio_url": "https://example.com/audio.mp3",
                      "content_image_url": "image",
                      "content_thumbnail_url": "thumb",
                      "content_title": "title",
                      "content_artist": "artist",
                      "content_album_title": "album",
                      "content_genre": "genre",
                      "content_description": "description",
                      "content_release_year": 2020
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
        val audioCache: AudioCacheManager = mockk {
            coEvery { resolve(any(), any()) } throws IllegalStateException("cache error")
        }
        val repository = LessonRepositoryImpl(
            client = client,
            dispatchers = dispatchers,
            mapper = LessonMapper(),
            audioCache = audioCache,
        )

        val result = repository.getLesson("1")

        assertTrue(result.lessonContent.isNotEmpty())
        assertEquals(
            "https://example.com/audio.mp3",
            result.lessonContent.first().contentAudioUrl
        )
    }
}
