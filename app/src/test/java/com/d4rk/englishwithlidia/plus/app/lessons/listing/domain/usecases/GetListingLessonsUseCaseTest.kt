/*
package com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.repository.ListingRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Test

class GetListingLessonsUseCaseTest {

    private val repository: ListingRepository = mockk()
    private val useCase = GetListingLessonsUseCase(repository)

    @Test
    fun `invoke calls repository once`() {
        every { repository.getListingLessons() } returns flowOf(ListingScreen())

        useCase()

        verify(exactly = 1) { repository.getListingLessons() }
    }
}

*/
