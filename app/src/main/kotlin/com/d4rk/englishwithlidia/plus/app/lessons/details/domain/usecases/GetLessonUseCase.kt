package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.coroutines.flow.Flow

class GetLessonUseCase(
    private val repository: LessonRepository,
) {
    operator fun invoke(lessonId: String): Flow<DataState<Lesson, AppErrors>> =
        repository.getLesson(lessonId)
}
