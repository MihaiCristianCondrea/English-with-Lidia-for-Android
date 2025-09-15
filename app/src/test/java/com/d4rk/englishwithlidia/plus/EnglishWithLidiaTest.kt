package com.d4rk.englishwithlidia.plus

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.d4rk.android.libs.apptoolkit.app.support.billing.BillingRepository
import com.d4rk.android.libs.apptoolkit.data.core.ads.AdsCoreManager
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.junit5.RobolectricExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(RobolectricExtension::class)
@Config(manifest = Config.NONE)
class EnglishWithLidiaTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val adsCoreManager: AdsCoreManager = mockk(relaxed = true)
    private val billingRepository: BillingRepository = mockk(relaxed = true)
    private lateinit var app: EnglishWithLidia

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(
                module {
                    single { adsCoreManager }
                    single { billingRepository }
                }
            )
        }
        app = EnglishWithLidia()
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `currentActivity is set and cleared`() {
        val activity = Robolectric.buildActivity(Activity::class.java).setup().get()
        app.onActivityStarted(activity)
        val field = EnglishWithLidia::class.java.getDeclaredField("currentActivity").apply { isAccessible = true }
        org.junit.jupiter.api.Assertions.assertSame(activity, field.get(app))
        app.onActivityStopped(activity)
        org.junit.jupiter.api.Assertions.assertNull(field.get(app))
    }

    @Test
    fun `onStart shows ad when activity present`() {
        val activity = Robolectric.buildActivity(Activity::class.java).setup().get()
        val owner = testLifecycleOwner()
        app.onActivityStarted(activity)
        app.onStart(owner)
        verify { adsCoreManager.showAdIfAvailable(activity, any()) }
    }

    @Test
    fun `onResume processes past purchases`() = runTest {
        val owner = testLifecycleOwner()
        app.onResume(owner)
        advanceUntilIdle()
        coVerify { billingRepository.processPastPurchases() }
    }

    private fun testLifecycleOwner(): LifecycleOwner = object : LifecycleOwner {
        private val registry = LifecycleRegistry(this).apply {
            currentState = Lifecycle.State.STARTED
        }
        override fun getLifecycle(): Lifecycle = registry
    }
}

