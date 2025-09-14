package com.d4rk.englishwithlidia.plus.app.lessons.list.data

import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeLesson
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.HomeScreen
import com.d4rk.englishwithlidia.plus.core.domain.model.api.ApiHomeResponse

/**
 * Maps network responses into domain models for the home lessons screen.
 */
class HomeMapper {
    fun map(response: ApiHomeResponse): HomeScreen {
        val lessons = response.data.map { networkLesson ->
            HomeLesson(
                lessonId = networkLesson.lessonId,
                lessonTitle = networkLesson.lessonTitle,
                lessonType = networkLesson.lessonType,
                lessonThumbnailImageUrl = networkLesson.lessonThumbnailImageUrl,
                lessonDeepLinkPath = networkLesson.lessonDeepLinkPath,
            )
        }
        return HomeScreen(lessons = lessons)
    }
}

