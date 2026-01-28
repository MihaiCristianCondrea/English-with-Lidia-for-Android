package com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.repository

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.coroutines.flow.Flow

interface ListingRepository {
    fun fetchListingLessons(): Flow<DataState<ListingScreen, AppErrors>>
}
