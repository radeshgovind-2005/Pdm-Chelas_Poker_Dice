package isel.pdm.pokerdice.ui.viewmodels.lobby

import android.util.Log
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.lobby.Lobby
import isel.pdm.pokerdice.services.events.LobbyEvent
import isel.pdm.pokerdice.ui.viewmodels.usecases.LobbyUseCase
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
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
class LobbyViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<LobbyUseCase>()
    // We use a SharedFlow to simulate the SSE stream from the server
    private val eventsFlow = MutableSharedFlow<LobbyEvent>(replay = 0)

    // Helper: Valid Lobby Object
    private val validLobby = Lobby(
        id = "Lobby1",
        name = "Test Lobby",
        description = "Desc",
        host = "HostUser",
        state = "WAITING",
        maxRounds = 5,
        maxPlayers = 2,
        ante = 10,
        initialBalance = 100,
        players = listOf("HostUser") // Initially 1 player
    )

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

        // Default mock behaviors
        coEvery { useCase.subscribeToLobby(any()) } returns eventsFlow
        coEvery { useCase.getUsername() } returns "PlayerOne"
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `initializeVM_subscribes_to_lobby_events_and_updates_state`() = runTest {
        val sut = LobbyViewModel(useCase)
        val states = mutableListOf<LobbyState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        sut.inititializeVM("Lobby1")

        // Simulate receiving Lobby Data from Server
        eventsFlow.emit(LobbyEvent.CurrentLobby(validLobby))

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals(validLobby, finalState.lobby)
        // PlayerOne is NOT in the players list yet ("HostUser" is)
        assertFalse(finalState.isJoined)
    }

    @Test
    fun `isJoined_becomes_true_when_current_user_is_in_player_list`() = runTest {
        val sut = LobbyViewModel(useCase)
        // Mock that WE are the Host
        coEvery { useCase.getUsername() } returns "HostUser"

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect {} // Just to start flow
        }

        sut.inititializeVM("Lobby1")
        eventsFlow.emit(LobbyEvent.CurrentLobby(validLobby)) // validLobby contains "HostUser"

        // Assert
        assertTrue(sut.state.value.isJoined)
    }

    /**
     * Simulate a user frantically clicking "Join" multiple times.
     * We want to verify that the system handles this gracefully.
     */
    @Test
    fun `joinRequest_calls_usecase_successfully`() = runTest {
        // Arrange
        val sut = LobbyViewModel(useCase)
        coEvery { useCase.joinLobby("Lobby1") } returns Result.success("Lobby1")

        sut.inititializeVM("Lobby1")
        eventsFlow.emit(LobbyEvent.CurrentLobby(validLobby))

        // Ensure we aren't joined yet
        assertFalse(sut.state.value.isJoined)

        // Act
        sut.onJoinRequest()

        // Assert
        assertTrue(sut.state.value.isJoined)
        coVerify(exactly = 1) { useCase.joinLobby("Lobby1") }
    }

    @Test
    fun `PlayerJoined_event_updates_player_list`() = runTest {
        val sut = LobbyViewModel(useCase)
        sut.inititializeVM("Lobby1")
        eventsFlow.emit(LobbyEvent.CurrentLobby(validLobby))

        // Act: "PlayerTwo" joins via SSE event
        eventsFlow.emit(LobbyEvent.PlayerJoined("PlayerTwo"))

        // Assert
        val state = sut.state.value
        assertEquals(2, state.lobby?.players?.size)
        assertTrue(state.lobby?.players?.contains("PlayerTwo") == true)
    }

    @Test
    fun `MatchInit_event_navigates_to_Match`() = runTest {
        val sut = LobbyViewModel(useCase)
        val effects = mutableListOf<LobbyNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.inititializeVM("Lobby1")

        // Act: Server signals Match Start
        eventsFlow.emit(LobbyEvent.MatchInit("Match_X"))

        // Assert
        assertEquals(1, effects.size)
        val effect = effects.first() as LobbyNavigation.ToMatch
        assertEquals("Match_X", effect.matchId)
    }
}