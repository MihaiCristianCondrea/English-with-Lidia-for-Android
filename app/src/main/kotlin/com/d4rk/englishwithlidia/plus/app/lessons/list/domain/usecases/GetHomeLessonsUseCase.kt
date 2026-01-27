package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.coroutines.flow.Flow

class GetHomeLessonsUseCase(
    private val repository: HomeRepository,
) {
    operator fun invoke(): Flow<DataState<HomeScreen, AppErrors>> =
        repository.fetchHomeLessons()
}
