package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow

class GetLessonUseCase(private val repository: LessonRepository) {
    operator fun invoke(lessonId: String): Flow<Lesson?> {
        return repository.getLesson(lessonId)
    }
}
