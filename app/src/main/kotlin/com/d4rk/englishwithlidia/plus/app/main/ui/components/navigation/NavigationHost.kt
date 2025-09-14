package com.d4rk.englishwithlidia.plus.app.main.ui.components.navigation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.d4rk.englishwithlidia.plus.app.lessons.list.domain.model.ui.UiHomeLesson

fun openLessonDetailActivity(context: Context, lesson: UiHomeLesson) {
    Intent(Intent.ACTION_VIEW, lesson.lessonDeepLinkPath.toUri()).let { intent: Intent ->
        intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent)
        }
    }
}