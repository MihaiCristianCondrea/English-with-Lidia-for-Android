package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.navigation

import com.d4rk.android.libs.apptoolkit.core.ui.navigation.NavigationEntryBuilder
import com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.ListingRoute
import com.d4rk.englishwithlidia.plus.app.main.ui.views.navigation.AppNavigationEntryContext
import com.d4rk.englishwithlidia.plus.app.main.utils.constants.AppNavKey
import com.d4rk.englishwithlidia.plus.app.main.utils.constants.BrightnessRoute

fun listingEntryBuilder(
    context: AppNavigationEntryContext,
): NavigationEntryBuilder<AppNavKey> = {
    entry<BrightnessRoute> {
        ListingRoute(
            paddingValues = context.paddingValues,
        )
    }
}
