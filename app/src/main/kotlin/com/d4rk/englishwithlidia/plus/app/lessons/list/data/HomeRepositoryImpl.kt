package com.d4rk.englishwithlidia.plus.app.lessons.list.data

import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.app.lessons.list.data.remote.HomeRemoteDataSource
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepositoryImpl(
    private val dispatchers: DispatcherProvider,
    private val mapper: HomeMapper,
    private val remoteDataSource: HomeRemoteDataSource,
) : HomeRepository {

    override fun getHomeLessons(): Flow<HomeScreen> =
        flow {
            val response = remoteDataSource.fetchHomeLessons()
            val homeScreen = response
                ?.takeIf { it.data.isNotEmpty() }
                ?.let(mapper::map)
                ?: HomeScreen()

            emit(homeScreen)
        }.catch { throwable ->
            if (throwable is CancellationException) throw throwable
            emit(HomeScreen())
        }.flowOn(dispatchers.io)
}
