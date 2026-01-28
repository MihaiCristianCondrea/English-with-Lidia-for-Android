package com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.usecases

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.repository.ListingRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.coroutines.flow.Flow

class GetListingLessonsUseCase(
    private val repository: ListingRepository,
) {
    operator fun invoke(): Flow<DataState<ListingScreen, AppErrors>> =
        repository.fetchListingLessons()
}
