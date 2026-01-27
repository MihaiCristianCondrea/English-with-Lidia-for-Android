package com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers

import android.content.Context
import android.os.Build
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.app.settings.utils.providers.AboutSettingsProvider
import com.d4rk.englishwithlidia.plus.BuildConfig

class AppAboutSettingsProvider(val context: Context) : AboutSettingsProvider {
    override val deviceInfo: String
        get() {
            return context.getString(
                R.string.app_build,
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.manufacturer)} ${Build.MANUFACTURER}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.device_model)} ${Build.MODEL}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.android_version)} ${Build.VERSION.RELEASE}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.api_level)} ${Build.VERSION.SDK_INT}",
                "${context.getString(com.d4rk.android.libs.apptoolkit.R.string.arch)} ${Build.SUPPORTED_ABIS.joinToString()}",
                if (BuildConfig.DEBUG) context.getString(com.d4rk.android.libs.apptoolkit.R.string.debug) else context.getString(
                    com.d4rk.android.libs.apptoolkit.R.string.release
                )
            )
        }
}