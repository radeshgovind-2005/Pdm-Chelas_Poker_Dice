package isel.pdm.pokerdice.ui.viewmodels.browse

import android.util.Log
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.model.lobby.BrowseLobby
import isel.pdm.pokerdice.events.LobbiesEvents
import isel.pdm.pokerdice.domain.usecases.BrowseUseCase
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
class BrowseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<BrowseUseCase>()
    // We use a SharedFlow to simulate the "Server Sent Events" stream dynamically
    private val lobbiesFlow = MutableSharedFlow<LobbiesEvents>(replay = 0)

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.v(any(), any()) } returns 0

        // Mock the UseCase to return our controllable Flow
        coEvery { useCase.subscribeToLobbies() } returns lobbiesFlow
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `init_subscribes_to_lobbies_and_handles_Init_event`() = runTest {
        // Arrange
        val sut = BrowseViewModel(useCase)
        val states = mutableListOf<BrowseState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        val lobby1 = BrowseLobby("1", "Table A", 10, "Host1")
        val lobby2 = BrowseLobby("2", "Table B", 20, "Host2")

        // Act: Simulate Server sending INIT event
        lobbiesFlow.emit(LobbiesEvents.Init(listOf(lobby1, lobby2)))

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals(2, finalState.lobbies.size)
        assertEquals(2, finalState.filteredLobbies.size)
        assertEquals("Table A", finalState.lobbies[0].name)
    }

    @Test
    fun `filtering_logic_works_correctly`() = runTest {
        // Arrange
        val sut = BrowseViewModel(useCase)
        val lobby1 = BrowseLobby("1", "Poker Night", 10, "Alice")
        val lobby2 = BrowseLobby("2", "Dice Roll", 20, "Bob")

        // Initialize list
        lobbiesFlow.emit(LobbiesEvents.Init(listOf(lobby1, lobby2)))

        // Act 1: Filter by "Poker"
        sut.onQueryChange("Poker")

        // Assert 1
        assertEquals(2, sut.state.value.lobbies.size) // Total remains 2
        assertEquals(1, sut.state.value.filteredLobbies.size) // Filtered is 1
        assertEquals("Poker Night", sut.state.value.filteredLobbies[0].name)

        // Act 2: Clear Filter
        sut.onQueryChange("")

        // Assert 2
        assertEquals(2, sut.state.value.filteredLobbies.size)
    }

    @Test
    fun `Add_event_updates_list_and_respects_current_filter`() = runTest {
        // Arrange
        val sut = BrowseViewModel(useCase)
        val lobby1 = BrowseLobby("1", "Alpha", 10, "HostA")

        lobbiesFlow.emit(LobbiesEvents.Init(listOf(lobby1)))

        // Set a filter that matches "Beta" (which hasn't arrived yet)
        sut.onQueryChange("Beta")
        assertEquals(0, sut.state.value.filteredLobbies.size)

        // Act: Server sends "Beta" lobby
        val lobbyNew = BrowseLobby("2", "Beta Room", 10, "HostB")
        lobbiesFlow.emit(LobbiesEvents.Add(lobbyNew))

        // Assert
        val state = sut.state.value
        assertEquals(2, state.lobbies.size) // Both exist
        assertEquals(1, state.filteredLobbies.size) // Only Beta is shown
        assertEquals("Beta Room", state.filteredLobbies[0].name)
    }

    @Test
    fun `Remove_event_updates_list`() = runTest {
        // Arrange
        val sut = BrowseViewModel(useCase)
        val lobby1 = BrowseLobby("1", "To Stay", 10, "HostA")
        val lobby2 = BrowseLobby("2", "To Remove", 10, "HostB")

        lobbiesFlow.emit(LobbiesEvents.Init(listOf(lobby1, lobby2)))
        assertEquals(2, sut.state.value.lobbies.size)

        // Act: Server says remove ID "2"
        lobbiesFlow.emit(LobbiesEvents.Remove("2"))

        // Assert
        val state = sut.state.value
        assertEquals(1, state.lobbies.size)
        assertEquals("To Stay", state.lobbies[0].name)
    }

    @Test
    fun `Navigation_events_emit_correct_effects`() = runTest {
        val sut = BrowseViewModel(useCase)
        val effects = mutableListOf<BrowseNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        // Test Back
        sut.onBackRequest()
        assertTrue(effects.last() is BrowseNavigation.ToTitle)

        // Test Create
        sut.onCreateRequest()
        assertTrue(effects.last() is BrowseNavigation.ToCreateLobby)

        // Test Lobby Selection
        sut.onLobbyRequest("Lobby123")
        val effect = effects.last() as BrowseNavigation.ToLobby
        assertEquals("Lobby123", effect.lobbyId)
    }
}