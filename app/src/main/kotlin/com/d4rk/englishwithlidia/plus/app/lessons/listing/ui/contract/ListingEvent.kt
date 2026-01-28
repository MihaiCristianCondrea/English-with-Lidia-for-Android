package com.d4rk.englishwithlidia.plus.app.lessons.listing.ui.contract

import com.d4rk.android.libs.apptoolkit.core.ui.base.handling.UiEvent

sealed interface ListingEvent : UiEvent {
    data object LoadLessons : ListingEvent
    data object DismissSnackbar : ListingEvent
}
