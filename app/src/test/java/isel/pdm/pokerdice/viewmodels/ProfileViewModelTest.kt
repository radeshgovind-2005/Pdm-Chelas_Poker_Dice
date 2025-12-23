package isel.pdm.pokerdice.ui.viewmodels.profile

import android.util.Log
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.model.user.UserStats
import isel.pdm.pokerdice.domain.usecases.ProfileUseCase
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<ProfileUseCase>()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.w(any(), any<Throwable>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `onCreateActivity_fetches_stats_successfully`() = runTest {
        // Arrange
        val sut = ProfileViewModel(useCase)
        val mockStats = UserStats(
            gamesPlayed = "10",
            matchesWon = "5",
            winRate = "50%",
            roundsWon = "20",
            lobbiesHosted = "2",
            invitesSent = "0",
            epicHands = "1",
            totalBalance = "1000"
        )
        val mockUsername = "PlayerOne"

        coEvery { useCase.getStats() } returns Result.success(Pair(mockStats, mockUsername))

        val states = mutableListOf<ProfileState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        sut.onCreateActivity()

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals(mockUsername, finalState.username)
        assertNotNull(finalState.stats)
        assertEquals("10", finalState.stats?.gamesPlayed)
    }

    @Test
    fun `onCreateActivity_handles_fetch_failure`() = runTest {
        // Arrange
        val sut = ProfileViewModel(useCase)
        coEvery { useCase.getStats() } returns Result.failure(Exception("Network Error"))

        val states = mutableListOf<ProfileState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        sut.onCreateActivity()

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals(null, finalState.stats)
        // Ensure it didn't crash and just stayed in a safe state
    }

    @Test
    fun `logout_dialog_logic_works_correctly`() = runTest {
        val sut = ProfileViewModel(useCase)

        // Initial state
        assertFalse(sut.state.value.showLogoutDialog)

        // Request Logout
        sut.onLogoutRequest()
        assertTrue(sut.state.value.showLogoutDialog)

        // Cancel Logout
        sut.onLogoutCancel()
        assertFalse(sut.state.value.showLogoutDialog)
    }

    @Test
    fun `onLogoutConfirm_calls_usecase_and_navigates`() = runTest {
        // Arrange
        val sut = ProfileViewModel(useCase)
        coEvery { useCase.logout() } returns Result.success(Unit)

        val effects = mutableListOf<ProfileNavigation>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        // Act
        sut.onLogoutConfirm()

        // Assert
        // 1. Verify Dialog Closed
        assertFalse(sut.state.value.showLogoutDialog)

        // 2. Verify UseCase called
        coVerify(exactly = 1) { useCase.logout() }

        // 3. Verify Navigation
        assertEquals(1, effects.size)
        assertTrue(effects.first() is ProfileNavigation.ToLogin)
    }
}