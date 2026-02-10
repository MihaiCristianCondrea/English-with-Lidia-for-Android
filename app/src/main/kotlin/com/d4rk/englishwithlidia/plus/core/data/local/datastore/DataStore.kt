package com.d4rk.englishwithlidia.plus.core.data.local.datastore

import android.content.Context
import com.d4rk.android.libs.apptoolkit.core.data.local.datastore.CommonDataStore
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.englishwithlidia.plus.BuildConfig

class DataStore(
    context: Context,
    dispatchers: DispatcherProvider,
    defaultAdsEnabled: Boolean = !BuildConfig.DEBUG,
) : CommonDataStore(context, dispatchers, defaultAdsEnabled)