package com.d4rk.englishwithlidia.plus.app.lessons.listing.data.repository

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.android.libs.apptoolkit.core.domain.model.network.Errors
import com.d4rk.android.libs.apptoolkit.core.utils.constants.api.ApiEnvironments
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.errors.toError
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.mapper.toDomain
import com.d4rk.englishwithlidia.plus.app.lessons.listing.data.remote.ListingDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.model.ListingScreen
import com.d4rk.englishwithlidia.plus.app.lessons.listing.domain.repository.ListingRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import com.d4rk.englishwithlidia.plus.core.utils.constants.api.EnglishWithLidiaApiEndpoints
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException

class ListingRepositoryImpl(
    private val remoteDataSource: ListingDataSource,
) : ListingRepository {

    override fun fetchListingLessons(): Flow<DataState<ListingScreen, AppErrors>> =
        flow<DataState<ListingScreen, AppErrors>> {
            val environment =
                if (BuildConfig.DEBUG) ApiEnvironments.ENV_DEBUG else ApiEnvironments.ENV_RELEASE

            val urlString = EnglishWithLidiaApiEndpoints.homeLessons(environment = environment)
            val response = remoteDataSource.fetchListingLessons(urlString = urlString)

            val screen = response.toDomain()

            emit(DataState.Success(data = screen))
        }.catch { throwable ->
            if (throwable is CancellationException) throw throwable

            val error: AppErrors = when (throwable) {
                is SerializationException -> AppErrors.UseCase.FAILED_TO_PARSE_LESSONS
                else -> AppErrors.Common(
                    throwable.toError(default = Errors.Network.UNKNOWN)
                )
            }

            emit(DataState.Error(error = error))
        }
}
