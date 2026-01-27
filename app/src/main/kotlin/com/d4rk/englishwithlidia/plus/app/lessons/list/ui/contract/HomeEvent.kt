package com.d4rk.englishwithlidia.plus.app.lessons.list.ui.contract

import com.d4rk.android.libs.apptoolkit.core.ui.base.handling.UiEvent

sealed interface HomeEvent : UiEvent {
    data object LoadLessons : HomeEvent
    data object DismissSnackbar : HomeEvent
}
