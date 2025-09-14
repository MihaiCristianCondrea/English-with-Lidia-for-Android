package com.d4rk.englishwithlidia.plus.app.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.d4rk.android.libs.apptoolkit.app.main.utils.InAppUpdateHelper
import com.d4rk.android.libs.apptoolkit.app.startup.ui.StartupActivity
import com.d4rk.android.libs.apptoolkit.app.theme.style.AppTheme
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.ConsentFormHelper
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.ConsentManagerHelper
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.IntentsHelper
import com.d4rk.android.libs.apptoolkit.core.utils.helpers.ReviewHelper
import com.d4rk.englishwithlidia.plus.core.data.datastore.DataStore
import com.google.android.gms.ads.MobileAds
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.ump.ConsentInformation
import com.google.android.ump.UserMessagingPlatform
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val dataStore: DataStore by inject()
    private val dispatchers: DispatcherProvider by inject()
    private var updateResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {}
    private var keepSplashVisible: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashVisible }
        enableEdgeToEdge()
        initializeDependencies()
        handleStartup()
        checkInAppReview()
    }

    override fun onResume() {
        super.onResume()
        checkForUpdates()
        checkUserConsent()
    }

    private fun initializeDependencies() {
        lifecycleScope.launch {
            coroutineScope {
                val adsInitialization = async(dispatchers.default) { MobileAds.initialize(this@MainActivity) {} }
                val consentInitialization = async(dispatchers.io) {
                    ConsentManagerHelper.applyInitialConsent(dataStore)
                }
                awaitAll(adsInitialization, consentInitialization)
            }
        }
    }

    private fun handleStartup() {
        lifecycleScope.launch {
            val isFirstLaunch: Boolean = withContext(dispatchers.io) {
                dataStore.startup.first()
            }
            keepSplashVisible = false
            if (isFirstLaunch) {
                startStartupActivity()
            } else {
                setMainActivityContent()
            }
        }
    }

    private fun startStartupActivity() {
        IntentsHelper.openActivity(context = this, activityClass = StartupActivity::class.java)
        finish()
    }

    private fun setMainActivityContent() {
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }

    private fun checkUserConsent() {
        lifecycleScope.launch {
            val consentInfo: ConsentInformation = withContext(dispatchers.io) {
                UserMessagingPlatform.getConsentInformation(this@MainActivity)
            }
            ConsentFormHelper.showConsentFormIfRequired(
                activity = this@MainActivity,
                consentInfo = consentInfo
            )
        }
    }

    private fun checkInAppReview() {
        lifecycleScope.launch {
            val (sessionCount: Int, hasPrompted: Boolean) = withContext(dispatchers.io) {
                val sc = dataStore.sessionCount.first()
                val hp = dataStore.hasPromptedReview.first()
                sc to hp
            }
            ReviewHelper.launchInAppReviewIfEligible(
                activity = this@MainActivity,
                sessionCount = sessionCount,
                hasPromptedBefore = hasPrompted,
                scope = this
            ) {
                launch(dispatchers.io) { dataStore.setHasPromptedReview(value = true) }
            }
            withContext(dispatchers.io) { dataStore.incrementSessionCount() }
        }
    }

    private fun checkForUpdates() {
        lifecycleScope.launch {
            withContext(dispatchers.io) {
                InAppUpdateHelper.performUpdate(
                    appUpdateManager = AppUpdateManagerFactory.create(this@MainActivity),
                    updateResultLauncher = updateResultLauncher,
                )
            }
        }
    }
}