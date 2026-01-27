package com.d4rk.englishwithlidia.plus.app.lessons.details.domain.repository

import com.d4rk.android.libs.apptoolkit.core.domain.model.network.DataState
import com.d4rk.englishwithlidia.plus.app.lessons.details.domain.model.Lesson
import com.d4rk.englishwithlidia.plus.core.domain.model.network.AppErrors
import kotlinx.coroutines.flow.Flow

interface LessonRepository {
    fun getLesson(lessonId: String): Flow<DataState<Lesson, AppErrors>>
}
