package com.d4rk.englishwithlidia.plus.core.utils.constants.ads

import com.d4rk.android.libs.apptoolkit.core.utils.constants.ads.DebugAdsConstants
import com.d4rk.englishwithlidia.plus.BuildConfig

object AdsConstants {

    private fun nativeAdUnitId(releaseId: String): String =
        if (BuildConfig.DEBUG) {
            DebugAdsConstants.NATIVE_AD_UNIT_ID
        } else {
            releaseId
        }

    val APP_OPEN_UNIT_ID: String
        get() = if (BuildConfig.DEBUG) {
            DebugAdsConstants.APP_OPEN_AD_UNIT_ID
        } else {
            "ca-app-pub-5294151573817700/2885662643"
        }

    val NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/5578142927")

    val APP_DETAILS_NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/8490774272")

    val APPS_LIST_NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/4743100951")

    val NO_DATA_NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/3430019286")

    val BOTTOM_NAV_BAR_NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/6982251485")

    val HELP_NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/7512912137")

    val SUPPORT_NATIVE_AD_UNIT_ID: String
        get() = nativeAdUnitId("ca-app-pub-5294151573817700/9755754484")
}
