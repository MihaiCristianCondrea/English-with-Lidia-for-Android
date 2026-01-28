package com.d4rk.englishwithlidia.plus.app.main.ui.views.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Stable
import androidx.core.net.toUri
import com.d4rk.android.libs.apptoolkit.core.ui.navigation.NavigationEntryBuilder
import com.d4rk.android.libs.apptoolkit.core.ui.window.AppWindowWidthSizeClass
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.navigation.brightnessEntryBuilder
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.state.HomeLessonUiModel
import com.d4rk.englishwithlidia.plus.app.main.utils.constants.AppNavKey

/**
 * Context shared by all navigation entry builders in the app module.
 */
@Stable
data class AppNavigationEntryContext(
    val paddingValues: PaddingValues,
    val windowWidthSizeClass: AppWindowWidthSizeClass,
)

/**
 * Default app navigation builders that can be extended with additional entries.
 */
fun appNavigationEntryBuilders(
    context: AppNavigationEntryContext,
    additionalEntryBuilders: List<NavigationEntryBuilder<AppNavKey>> = emptyList(),
): List<NavigationEntryBuilder<AppNavKey>> = buildList {
    addAll(defaultAppNavigationEntryBuilders(context))
    addAll(additionalEntryBuilders)
}

private fun defaultAppNavigationEntryBuilders(
    context: AppNavigationEntryContext,
): List<NavigationEntryBuilder<AppNavKey>> = listOf(
    brightnessEntryBuilder(context),
)

fun openLessonDetailActivity(context: Context, lesson: HomeLessonUiModel) {
    Intent(Intent.ACTION_VIEW, lesson.lessonDeepLinkPath.toUri()).let { intent: Intent ->
        context.startActivity(intent)
    }
}
