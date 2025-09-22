package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository

import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {
    fun getLesson(lessonId: String): Flow<Lesson?>
}
