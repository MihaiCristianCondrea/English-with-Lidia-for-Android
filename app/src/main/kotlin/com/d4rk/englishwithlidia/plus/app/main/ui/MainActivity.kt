package com.d4rk.englishwithlidia.plus.app.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.d4rk.android.libs.apptoolkit.app.consent.domain.usecases.ApplyInitialConsentUseCase
import com.d4rk.android.libs.apptoolkit.app.main.ui.factory.GmsHostFactory
import com.d4rk.android.libs.apptoolkit.app.startup.ui.StartupActivity
import com.d4rk.android.libs.apptoolkit.app.theme.ui.style.AppTheme
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import com.d4rk.android.libs.apptoolkit.core.utils.extensions.context.openActivity
import com.d4rk.englishwithlidia.plus.app.main.ui.contract.MainAction
import com.d4rk.englishwithlidia.plus.app.main.ui.contract.MainEvent
import com.d4rk.englishwithlidia.plus.core.data.local.datastore.DataStore
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val dataStore: DataStore by inject()
    private val dispatchers: DispatcherProvider by inject()
    private val viewModel: MainViewModel by viewModel()
    private val applyInitialConsentUseCase: ApplyInitialConsentUseCase by inject()
    private val gmsHostFactory: GmsHostFactory by inject()
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
        observeActions()
    }

    override fun onResume() {
        super.onResume()
        handleGmsEvents()
    }

    private fun initializeDependencies() {
        lifecycleScope.launch {
            coroutineScope {
                val adsInitialization =
                    async(dispatchers.default) { MobileAds.initialize(this@MainActivity) {} }
                val consentInitialization =
                    async(dispatchers.io) { applyInitialConsentUseCase.invoke() }
                awaitAll(adsInitialization, consentInitialization)
            }
        }
    }

    private fun handleStartup() {
        lifecycleScope.launch {
            val isFirstLaunch: Boolean = withContext(context = dispatchers.io) { dataStore.startup.first() }
            keepSplashVisible = false
            if (isFirstLaunch) {
                startStartupActivity()
            } else {
                setMainActivityContent()
            }
        }
    }

    private fun startStartupActivity() {
        openActivity(activityClass = StartupActivity::class.java)
        finish()
    }

    private fun setMainActivityContent() {
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }

    private fun observeActions() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actionEvent.collect { action ->
                    when (action) {
                        is MainAction.ReviewOutcomeReported -> Unit
                        is MainAction.InAppUpdateResultReported -> Unit
                    }
                }
            }
        }
    }

    private fun handleGmsEvents() {
        viewModel.onEvent(event = MainEvent.RequestConsent(host = gmsHostFactory.createConsentHost(activity = this)))
        viewModel.onEvent(event = MainEvent.RequestReview(host = gmsHostFactory.createReviewHost(activity = this)))
        viewModel.onEvent(event = MainEvent.RequestInAppUpdate(host = gmsHostFactory.createUpdateHost(activity = this, launcher = updateResultLauncher)))
    }
}
