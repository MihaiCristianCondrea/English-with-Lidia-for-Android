/*
 * Copyright (©) 2026 Mihai-Cristian Condrea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.d4rk.englishwithlidia.plus.core.utils.constants.ads

import com.d4rk.android.libs.apptoolkit.core.utils.constants.ads.DebugAdsConstants
import com.d4rk.englishwithlidia.plus.BuildConfig

object AdsConstants { // REMINDER: Check which of them are used and bring back the deleted ad unit id's

    private fun bannerAdUnitId(releaseId: String): String =
        if (BuildConfig.DEBUG) {
            DebugAdsConstants.BANNER_AD_UNIT_ID
        } else {
            releaseId
        }

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

    val BANNER_AD_UNIT_ID: String
        get() = bannerAdUnitId("ca-app-pub-5294151573817700/8479403125")

    val HELP_LARGE_BANNER_AD_UNIT_ID: String
        get() = bannerAdUnitId("ca-app-pub-5294151573817700/4295246186")

    val SUPPORT_MEDIUM_RECTANGLE_BANNER_AD_UNIT_ID: String
        get() = bannerAdUnitId("ca-app-pub-5294151573817700/4767671864")

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
