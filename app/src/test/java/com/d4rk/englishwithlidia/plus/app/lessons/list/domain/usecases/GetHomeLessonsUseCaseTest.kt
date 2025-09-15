package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Test

class GetHomeLessonsUseCaseTest {

    private val repository: HomeRepository = mockk()
    private val useCase = GetHomeLessonsUseCase(repository)

    @Test
    fun `invoke calls repository once`() {
        every { repository.getHomeLessons() } returns flowOf(HomeScreen())

        useCase()

        verify(exactly = 1) { repository.getHomeLessons() }
    }
}

