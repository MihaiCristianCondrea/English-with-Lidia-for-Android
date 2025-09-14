package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen

class GetLessonUseCase(private val repository: LessonRepository) {
    suspend operator fun invoke(lessonId: String): UiLessonScreen {
        return repository.getLesson(lessonId)
    }
}
