package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun fetchHomeLessons(): Flow<DataState<HomeScreen, AppErrors>>
}
