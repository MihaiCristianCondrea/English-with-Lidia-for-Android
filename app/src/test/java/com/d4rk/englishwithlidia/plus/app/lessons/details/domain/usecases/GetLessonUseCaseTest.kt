package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class GetLessonUseCaseTest {

    private val repository: LessonRepository = mockk()
    private val useCase = GetLessonUseCase(repository)

    @Test
    fun `invoke calls repository once`() = runTest {
        coEvery { repository.getLesson("id") } returns UiLessonScreen()

        useCase("id")

        coVerify(exactly = 1) { repository.getLesson("id") }
    }

    @Test
    fun `invoke returns repository value`() = runTest {
        val expected = UiLessonScreen(lessonTitle = "title")
        coEvery { repository.getLesson("id") } returns expected

        val result = useCase("id")

        assertEquals(expected, result)
    }

    @Test
    fun `invoke propagates exception`() = runTest {
        val exception = RuntimeException("error")
        coEvery { repository.getLesson("id") } throws exception

        try {
            useCase("id")
            fail("Exception expected")
        } catch (e: RuntimeException) {
            assertEquals(exception, e)
        }
    }
}

