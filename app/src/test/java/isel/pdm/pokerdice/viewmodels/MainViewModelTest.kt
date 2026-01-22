package isel.pdm.pokerdice.viewmodels

import android.util.Log
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.model.user.SessionInfo
import isel.pdm.pokerdice.domain.model.user.User
import isel.pdm.pokerdice.domain.usecases.MainUseCase
import isel.pdm.pokerdice.ui.viewmodels.main.MainNavigation
import isel.pdm.pokerdice.ui.viewmodels.main.MainState
import isel.pdm.pokerdice.ui.viewmodels.main.MainViewModel
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<MainUseCase>()

    @Before
    fun setup() {
        // Mock Android Log to prevent crashes
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
    fun `sessionCheck_navigates_to_Login_on_failure`() = runTest {
        // Arrange
        val sut = MainViewModel(useCase)
        coEvery { useCase.sessionCheck() } returns Result.failure(Exception("No Token"))

        val effects = mutableListOf<MainNavigation>()
        val states = mutableListOf<MainState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        sut.sessionCheck()

        // Assert
        // 1. Loading state was triggered
        assertTrue("Should show loading", states.any { it.isLoading })
        // 2. Loading finished
        assertFalse("Should finish loading", states.last().isLoading)
        // 3. Navigation
        assertEquals(1, effects.size)
        assertTrue(effects.first() is MainNavigation.ToLogin)
    }

    @Test
    fun `sessionCheck_navigates_to_Title_when_user_idle`() = runTest {
        // Arrange
        val sut = MainViewModel(useCase)
        val mockUser = mockk<User>()
        // SessionInfo with NULL lobbyId and matchId
        val idleSession = SessionInfo(id = 1, username = "User", lobbyId = null, matchId = null)

        coEvery { useCase.sessionCheck() } returns Result.success(Pair(mockUser, idleSession))

        val effects = mutableListOf<MainNavigation>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        // Act
        sut.sessionCheck()

        // Assert
        assertEquals(1, effects.size)
        assertTrue(effects.first() is MainNavigation.ToTitle)
    }

    @Test
    fun `sessionCheck_navigates_to_Lobby_when_user_in_lobby`() = runTest {
        // Arrange
        val sut = MainViewModel(useCase)
        val mockUser = mockk<User>()
        // SessionInfo with VALID lobbyId
        val lobbySession = SessionInfo(id = 1, username = "User", lobbyId = "Lobby123", matchId = null)

        coEvery { useCase.sessionCheck() } returns Result.success(Pair(mockUser, lobbySession))

        val effects = mutableListOf<MainNavigation>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        // Act
        sut.sessionCheck()

        // Assert
        assertEquals(1, effects.size)
        val navigation = effects.first()
        assertTrue(navigation is MainNavigation.ToLobby)
        assertEquals("Lobby123", (navigation as MainNavigation.ToLobby).lobbyId)
    }

    @Test
    fun `sessionCheck_navigates_to_Match_when_user_in_match`() = runTest {
        // Arrange
        val sut = MainViewModel(useCase)
        val mockUser = mockk<User>()
        // SessionInfo with VALID lobbyId AND matchId
        val matchSession = SessionInfo(id = 1, username = "User", lobbyId = "Lobby123", matchId = "Match456")

        coEvery { useCase.sessionCheck() } returns Result.success(Pair(mockUser, matchSession))

        val effects = mutableListOf<MainNavigation>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        // Act
        sut.sessionCheck()

        // Assert
        assertEquals(1, effects.size)
        val navigation = effects.first()
        assertTrue(navigation is MainNavigation.ToMatch)
        assertEquals("Match456", (navigation as MainNavigation.ToMatch).matchId)
    }
}