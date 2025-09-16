package com.d4rk.englishwithlidia.plus

import android.app.Activity
import android.content.ComponentCallbacks
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ProcessLifecycleOwner
import com.d4rk.android.libs.apptoolkit.app.support.billing.BillingRepository
import com.d4rk.android.libs.apptoolkit.data.core.BaseCoreManager
import com.d4rk.android.libs.apptoolkit.data.core.ads.AdsCoreManager
import com.d4rk.englishwithlidia.plus.core.di.initializeKoin
import io.mockk.any
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.koin.android.ext.android.getKoin
import org.koin.core.Koin
import org.koin.core.component.get
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class EnglishWithLidiaTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var koin: Koin
    private lateinit var adsCoreManager: AdsCoreManager
    private lateinit var billingRepository: BillingRepository
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var lifecycle: Lifecycle

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        adsCoreManager = mockk(relaxed = true)
        billingRepository = mockk(relaxed = true)
        lifecycle = mockk(relaxed = true)
        lifecycleOwner = mockk(relaxed = true) {
            every { lifecycle } returns lifecycle
        }

        koin = mockk(relaxed = true)

        mockkStatic("com.d4rk.englishwithlidia.plus.core.di.KoinModuleKt")
        every { initializeKoin(any()) } just runs

        mockkStatic("org.koin.android.ext.android.KoinExtKt")
        every { getKoin(any<ComponentCallbacks>()) } returns koin
        every { koin.get<AdsCoreManager>() } returns adsCoreManager

        mockkStatic(ProcessLifecycleOwner::class)
        every { ProcessLifecycleOwner.get() } returns lifecycleOwner
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `onCreate should initialize dependencies and register lifecycle observer`() {
        val sut = spyk(EnglishWithLidia(), recordPrivateCalls = true)

        assertDoesNotThrow {
            sut.onCreate()
            sut.onCreate()
        }

        verify(exactly = 2) { initializeKoin(sut) }
        verify(exactly = 2) { lifecycle.addObserver(sut) }
    }

    @Test
    fun `onResume should handle exceptions from billing repository`() = runTest {
        val sut = spyk(EnglishWithLidia(), recordPrivateCalls = true)
        sut.setBillingRepository(billingRepository)
        val owner = testLifecycleOwner()

        coEvery { billingRepository.processPastPurchases() } throws IllegalStateException("boom")

        assertDoesNotThrow {
            sut.onResume(owner)
        }

        advanceUntilIdle()

        coVerify(exactly = 1) { billingRepository.processPastPurchases() }
    }

    @Test
    fun `currentActivity should reflect started and stopped activities`() = runTest {
        val sut = spyk(EnglishWithLidia(), recordPrivateCalls = true)
        val owner = testLifecycleOwner()
        val activity = mockk<Activity>(relaxed = true)

        sut.onActivityStarted(activity)
        sut.onStart(owner)

        verify(exactly = 1) { adsCoreManager.showAdIfAvailable(activity, any()) }
        assertThatCurrentActivity(sut, activity)

        clearMocks(adsCoreManager)
        every { ProcessLifecycleOwner.get() } returns lifecycleOwner
        every { lifecycleOwner.lifecycle } returns lifecycle

        sut.onActivityStopped(activity)
        sut.onStart(owner)

        verify(exactly = 0) { adsCoreManager.showAdIfAvailable(any(), any()) }
        assertThatCurrentActivity(sut, null)

        sut.onActivityStarted(activity)
        assertThatCurrentActivity(sut, activity)

        sut.onActivityDestroyed(activity)
        assertThatCurrentActivity(sut, null)
    }

    private fun testLifecycleOwner(): LifecycleOwner {
        val owner = object : LifecycleOwner {
            private val registry = LifecycleRegistry(this)

            override val lifecycle: Lifecycle
                get() = registry

            fun markResumed() {
                registry.currentState = Lifecycle.State.RESUMED
            }
        }
        owner.markResumed()
        return owner
    }

    private fun EnglishWithLidia.setBillingRepository(repo: BillingRepository) {
        val field = BaseCoreManager::class.java.getDeclaredField("billingRepository")
        field.isAccessible = true
        field.set(this, repo)
    }

    private fun assertThatCurrentActivity(app: EnglishWithLidia, expected: Activity?) {
        val field = EnglishWithLidia::class.java.getDeclaredField("currentActivity")
        field.isAccessible = true
        val value = field.get(app)
        if (expected == null) {
            assertNull(value)
        } else {
            assertEquals(expected, value)
        }
    }
}
