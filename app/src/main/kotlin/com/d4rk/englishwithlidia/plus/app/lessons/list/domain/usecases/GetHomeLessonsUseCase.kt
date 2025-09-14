package com.d4rk.englishwithlidia.plus.app.lessons.list.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeScreen
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetHomeLessonsUseCase(private val repository: HomeRepository) {
    operator fun invoke(): Flow<UiHomeScreen> {
        return repository.getHomeLessons()
    }
}
