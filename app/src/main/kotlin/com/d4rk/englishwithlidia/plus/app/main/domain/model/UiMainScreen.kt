package com.d4rk.englishwithlidia.plus.app.main.domain.model

import com.d4rk.android.libs.apptoolkit.core.domain.model.navigation.NavigationDrawerItem
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.UiTextHelper

data class UiMainScreen(
    val showSnackbar: Boolean = false,
    val snackbarMessage: UiTextHelper? = null,
    val showDialog: Boolean = false,
    val navigationDrawerItems: List<NavigationDrawerItem> = listOf()
)