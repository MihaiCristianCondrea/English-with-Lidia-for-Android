package com.d4rk.englishwithlidia.plus.app.settings.settings.utils.providers

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.d4rk.android.libs.apptoolkit.R
import com.d4rk.android.libs.apptoolkit.core.di.DispatcherProvider
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PermissionsSettingsRepositoryTest {

    @Test
    fun `getPermissionsConfig includes expected special permissions`() = runTest {
        val context = createContextMock()
        val dispatchers = createDispatcherProvider(UnconfinedTestDispatcher(testScheduler))
        val repository = PermissionsSettingsRepository(context, dispatchers)

        val config = repository.getPermissionsConfig().first()

        val specialCategory = config.categories.first { it.title == SPECIAL_TITLE }
        assertEquals(1, specialCategory.preferences.size)
        val specialPreference = specialCategory.preferences.first()
        assertEquals(SPECIAL_PERMISSION_TITLE, specialPreference.title)
        assertEquals(SPECIAL_PERMISSION_SUMMARY, specialPreference.summary)
    }

    @Test
    fun `special permission action launches notification policy settings`() = runTest {
        val context = createContextMock()
        val intentSlot = slot<Intent>()
        every { context.startActivity(capture(intentSlot)) } just runs
        val dispatchers = createDispatcherProvider(UnconfinedTestDispatcher(testScheduler))
        val repository = PermissionsSettingsRepository(context, dispatchers)

        val specialPreference = repository.getPermissionsConfig().first()
            .categories.first { it.title == SPECIAL_TITLE }
            .preferences.first()

        specialPreference.action()

        verify(exactly = 1) { context.startActivity(any()) }
        val capturedIntent = intentSlot.captured
        assertEquals(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS, capturedIntent.action)
        assertTrue(
            capturedIntent.flags and Intent.FLAG_ACTIVITY_NEW_TASK == Intent.FLAG_ACTIVITY_NEW_TASK,
        )
    }

    private fun createContextMock(): Context {
        val context: Context = mockk(relaxed = true)
        every { context.getString(R.string.permissions) } returns "Permissions"
        every { context.getString(R.string.special) } returns SPECIAL_TITLE
        every { context.getString(R.string.access_notification_policy) } returns SPECIAL_PERMISSION_TITLE
        every {
            context.getString(R.string.summary_preference_permissions_access_notification_policy)
        } returns SPECIAL_PERMISSION_SUMMARY
        return context
    }

    private fun createDispatcherProvider(ioDispatcher: kotlinx.coroutines.CoroutineDispatcher): DispatcherProvider =
        mockk(relaxed = true) {
            every { io } returns ioDispatcher
        }

    private companion object {
        const val SPECIAL_TITLE = "Special"
        const val SPECIAL_PERMISSION_TITLE = "Notification policy access"
        const val SPECIAL_PERMISSION_SUMMARY = "Allows access to notification policy"
    }
}
