package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHomeLessons(): Flow<HomeScreen>
}
