package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.ui.UiLessonScreen

interface LessonRepository {
    suspend fun getLesson(lessonId: String): UiLessonScreen
}
