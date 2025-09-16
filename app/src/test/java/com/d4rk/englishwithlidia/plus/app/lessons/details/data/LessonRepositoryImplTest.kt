package com.d4rk.englishwithlidia.plus.app.lessons.details.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonContent
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.core.data.audio.AudioCacheManager
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLesson
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonContent
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiLessonResponse
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.ApiConstants
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LessonRepositoryImplTest {

    @Test
    fun `getLesson returns mapped screen when response is valid`() = runTest {
        val client: HttpClient = mockk()
        val response: HttpResponse = mockk()
        val audioCache: AudioCacheManager = mockk()
        val json: Json = mockk()
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache, json)

        val jsonString = "valid-json"
        coEvery { client.get(any<String>()) } returns response
        coEvery { response.bodyAsText() } returns jsonString

        val apiResponse = ApiLessonResponse(
            data = listOf(
                ApiLesson(
                    lessonTitle = "Lesson title",
                    lessonContent = listOf(
                        ApiLessonContent(
                            contentId = "content-id",
                            contentType = "audio",
                            contentText = "Content text",
                            contentAudioUrl = "https://remote/audio.mp3",
                            contentImageUrl = "https://remote/image.png",
                            contentThumbnailUrl = "https://remote/thumb.png",
                            contentTitle = "Title",
                            contentArtist = "Artist",
                            contentAlbumTitle = "Album",
                            contentGenre = "Genre",
                            contentDescription = "Description",
                            contentReleaseYear = 2024,
                        )
                    )
                )
            )
        )
        every { json.decodeFromString<ApiLessonResponse>(jsonString) } returns apiResponse

        val cachedUri = mockk<android.net.Uri> {
            every { toString() } returns "file:///cached/audio.mp3"
        }
        coEvery { audioCache.resolve("content-id", "https://remote/audio.mp3") } returns cachedUri

        val result = repository.getLesson("lesson_1")

        val expected = UiLessonScreen(
            lessonTitle = "Lesson title",
            lessonContent = listOf(
                UiLessonContent(
                    contentId = "content-id",
                    contentType = "audio",
                    contentText = "Content text",
                    contentAudioUrl = "file:///cached/audio.mp3",
                    contentImageUrl = "https://remote/image.png",
                    contentThumbnailUrl = "https://remote/thumb.png",
                    contentTitle = "Title",
                    contentArtist = "Artist",
                    contentAlbumTitle = "Album",
                    contentGenre = "Genre",
                    contentDescription = "Description",
                    contentReleaseYear = 2024,
                )
            )
        )
        assertEquals(expected, result)

        coVerify(exactly = 1) { audioCache.resolve("content-id", "https://remote/audio.mp3") }
    }

    @Test
    fun `getLesson returns empty screen when body is blank`() = runTest {
        val client: HttpClient = mockk()
        val response: HttpResponse = mockk()
        val audioCache: AudioCacheManager = mockk(relaxed = true)
        val json: Json = mockk(relaxed = true)
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache, json)

        coEvery { client.get(any<String>()) } returns response
        coEvery { response.bodyAsText() } returns ""

        val result = repository.getLesson("lesson_1")

        assertEquals(UiLessonScreen(), result)
        verify(exactly = 0) { json.decodeFromString<ApiLessonResponse>(any()) }
        coVerify(exactly = 0) { audioCache.resolve(any(), any()) }
    }

    @Test
    fun `getLesson falls back to remote url when cache returns original url`() = runTest {
        val client: HttpClient = mockk()
        val response: HttpResponse = mockk()
        val audioCache: AudioCacheManager = mockk()
        val json: Json = mockk()
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache, json)

        val jsonString = "valid-json"
        coEvery { client.get(any<String>()) } returns response
        coEvery { response.bodyAsText() } returns jsonString

        val remoteUrl = "https://remote/audio.mp3"
        val apiResponse = ApiLessonResponse(
            data = listOf(
                ApiLesson(
                    lessonTitle = "Lesson title",
                    lessonContent = listOf(
                        ApiLessonContent(
                            contentId = "content-id",
                            contentType = "audio",
                            contentText = "Content text",
                            contentAudioUrl = remoteUrl,
                            contentImageUrl = "",
                            contentThumbnailUrl = "",
                            contentTitle = "",
                            contentArtist = "",
                            contentAlbumTitle = "",
                            contentGenre = "",
                            contentDescription = "",
                            contentReleaseYear = null,
                        )
                    )
                )
            )
        )
        every { json.decodeFromString<ApiLessonResponse>(jsonString) } returns apiResponse

        val remoteUri = mockk<android.net.Uri> {
            every { toString() } returns remoteUrl
        }
        coEvery { audioCache.resolve("content-id", remoteUrl) } returns remoteUri

        val result = repository.getLesson("lesson_1")

        assertEquals(remoteUrl, result.lessonContent.first().contentAudioUrl)
    }

    @Test
    fun `getLesson uses debug base url when build type is debug`() = runTest {
        assumeTrue(BuildConfig.DEBUG)

        val client: HttpClient = mockk()
        val response: HttpResponse = mockk()
        val audioCache: AudioCacheManager = mockk(relaxed = true)
        val json: Json = mockk(relaxed = true)
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache, json)

        coEvery { client.get(any<String>()) } returns response
        coEvery { response.bodyAsText() } returns ""

        repository.getLesson("lesson_42")

        val expectedUrl = "${ApiConstants.BASE_REPOSITORY_URL}/debug/ro/lessons/api_get_lesson_42.json"
        coVerify(exactly = 1) { client.get(expectedUrl) }
    }

    @Test
    fun `getLesson uses release base url when build type is release`() = runTest {
        assumeTrue(!BuildConfig.DEBUG)

        val client: HttpClient = mockk()
        val response: HttpResponse = mockk()
        val audioCache: AudioCacheManager = mockk(relaxed = true)
        val json: Json = mockk(relaxed = true)
        val dispatchers: DispatcherProvider = mockk(relaxed = true) {
            every { io } returns UnconfinedTestDispatcher(testScheduler)
        }
        val repository = LessonRepositoryImpl(client, dispatchers, LessonMapper(), audioCache, json)

        coEvery { client.get(any<String>()) } returns response
        coEvery { response.bodyAsText() } returns ""

        repository.getLesson("lesson_42")

        val expectedUrl = "${ApiConstants.BASE_REPOSITORY_URL}/release/ro/lessons/api_get_lesson_42.json"
        coVerify(exactly = 1) { client.get(expectedUrl) }
    }
}

