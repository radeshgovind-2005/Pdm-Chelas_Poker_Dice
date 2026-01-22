package isel.pdm.pokerdice.app

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppLifecycleMonitorTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val context = mockk<Context>(relaxed = true)
    private val logger = mockk<AppLog>(relaxed = true)
    private val lifecycleOwner = mockk<LifecycleOwner>()


    @Test
    fun `onStop_starts_service_after_delay`() = runTest {
        val sut = AppLifecycleMonitor(context, logger)

        // Act: App goes to background
        sut.onStop(lifecycleOwner)

        // Assert 1: Immediately after stop, service should NOT be started yet (Debounce check)
        verify(exactly = 0) { context.startService(any()) }
        verify(exactly = 0) { context.startForegroundService(any()) }

        // Act: Fast forward time by 2001ms (just past the 2s delay)
        advanceTimeBy(2001)

        // Assert 2: Now the service MUST be started (using the available API)
        // Since SDK_INT is 0 in tests, it calls startService
        verify(exactly = 1) { context.startService(any()) }
    }

    @Test
    fun `onStop_does_NOT_start_service_if_back_to_foreground_quickly`() = runTest {
        val sut = AppLifecycleMonitor(context, logger)

        // Act 1: App goes to background
        sut.onStop(lifecycleOwner)

        // Act 2: Wait 1 second (less than the 2s threshold)
        advanceTimeBy(1000)

        // Act 3: User comes back to foreground!
        sut.onStart(lifecycleOwner)

        // Act 4: Wait more time to be sure the original timer would have fired
        advanceTimeBy(2000)

        // Assert: Service should NEVER have started because the job was cancelled
        verify(exactly = 0) { context.startService(any()) }
        verify(exactly = 0) { context.startForegroundService(any()) }

        // Verify we tried to stop it (cleanup)
        verify(exactly = 1) { context.stopService(any()) }
    }

    @Test
    fun `onStart_stops_service_immediately`() = runTest {
        val sut = AppLifecycleMonitor(context, logger)

        // Act
        sut.onStart(lifecycleOwner)

        // Assert
        verify(exactly = 1) { context.stopService(any()) }
    }
}