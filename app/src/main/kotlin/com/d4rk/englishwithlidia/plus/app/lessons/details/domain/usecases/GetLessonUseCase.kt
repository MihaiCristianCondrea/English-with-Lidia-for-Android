package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.usecases

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository.LessonRepository

class GetLessonUseCase(private val repository: LessonRepository) {
    suspend operator fun invoke(lessonId: String): UiLessonScreen {
        return repository.getLesson(lessonId)
    }
}
