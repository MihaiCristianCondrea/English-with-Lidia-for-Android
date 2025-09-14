package com.d4rk.englishwithlidia.plus.app.main.ui.components.navigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.d4rk.android.libs.apptoolkit.app.help.ui.HelpActivity
import com.d4rk.android.libs.apptoolkit.app.main.ui.components.navigation.NavigationHost
import com.d4rk.android.libs.apptoolkit.app.main.utils.constants.NavigationDrawerRoutes
import com.d4rk.android.libs.apptoolkit.app.settings.settings.ui.SettingsActivity
import com.d4rk.android.libs.apptoolkit.core.domain.model.navigation.NavigationDrawerItem
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.IntentsHelper
import com.d4rk.englishwithlidia.plus.app.lessons.list.ui.HomeRoute
import com.d4rk.englishwithlidia.plus.app.main.utils.constants.NavigationRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavigationHost(
    navController: NavHostController, paddingValues: PaddingValues
) {
    NavigationHost(
        navController = navController, startDestination = NavigationRoutes.ROUTE_LESSONS_LIST
    ) {
        composable(route = NavigationRoutes.ROUTE_LESSONS_LIST) {
            HomeRoute(paddingValues = paddingValues)
        }
    }
}

fun handleNavigationItemClick(
    context: Context,
    item: NavigationDrawerItem,
    drawerState: DrawerState? = null,
    coroutineScope: CoroutineScope? = null,
    onChangelogRequested: () -> Unit = {},
) {
    when (item.route) {
        NavigationDrawerRoutes.ROUTE_SETTINGS -> IntentsHelper.openActivity(
            context = context,
            activityClass = SettingsActivity::class.java
        )

        NavigationDrawerRoutes.ROUTE_HELP_AND_FEEDBACK -> IntentsHelper.openActivity(
            context = context,
            activityClass = HelpActivity::class.java
        )

        NavigationDrawerRoutes.ROUTE_UPDATES -> onChangelogRequested()
        NavigationDrawerRoutes.ROUTE_SHARE -> IntentsHelper.shareApp(
            context = context,
            shareMessageFormat = com.d4rk.android.libs.apptoolkit.R.string.summary_share_message
        )
    }
    if (drawerState != null && coroutineScope != null) {
        coroutineScope.launch { drawerState.close() }
    }
}
