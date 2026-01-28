package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class GetLessonUseCaseTest {

    private val repository: LessonRepository = mockk()
    private val useCase = GetLessonUseCase(repository)

    @Test
    fun `invoke calls repository once`() {
        every { repository.getLesson("id") } returns flowOf(DataState.Success(Lesson()))

        useCase("id")

        verify(exactly = 1) { repository.getLesson("id") }
    }

    @Test
    fun `invoke returns repository flow`() = runTest {
        val expected = DataState.Success<Lesson, AppErrors>(Lesson(lessonTitle = "title"))
        every { repository.getLesson("id") } returns flowOf(expected)

        val result = useCase("id")

        assertEquals(expected, result.single())
    }

    @Test
    fun `invoke propagates exception`() {
        val exception = RuntimeException("error")
        every { repository.getLesson("id") } throws exception

        try {
            useCase("id")
            fail("Exception expected")
        } catch (e: RuntimeException) {
            assertEquals(exception, e)
        }
    }
}
