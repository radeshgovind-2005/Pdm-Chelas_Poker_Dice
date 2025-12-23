package isel.pdm.pokerdice.ui.viewmodels.match

import android.util.Log
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.model.match.Game
import isel.pdm.pokerdice.domain.model.match.LobbyContent
import isel.pdm.pokerdice.domain.model.match.MatchContent
import isel.pdm.pokerdice.domain.model.match.MatchPlayers
import isel.pdm.pokerdice.domain.model.match.Round
import isel.pdm.pokerdice.events.MatchEvents
import isel.pdm.pokerdice.domain.usecases.MatchUseCase
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MatchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<MatchUseCase>()
    // We use a SharedFlow to simulate the SSE stream from the server
    private val eventsFlow = MutableSharedFlow<MatchEvents>(replay = 0)

    // Helper: Create a Dummy Game State for testing
    private fun createGame(
        turnPlayer: String = "PlayerOne",
        rerolls: Int = 3,
        hand: String = "AAAAA"
    ): Game {
        val player = MatchPlayers(
            name = turnPlayer,
            hand = hand,
            rank = "Five of a Kind",
            balance = 100,
            state = "turn",
            rerollsLeft = rerolls
        )
        return Game(
            username = "PlayerOne", // We assume current logged-in user is PlayerOne
            match = MatchContent("M1", "RUNNING", isStarted = true, isCompleted = false),
            lobby = LobbyContent("L1", "Test Lobby", 10),
            round = Round(5, 1, 10, listOf(player)),
            msg = null
        )
    }

    @Before
    fun setup() {
        // 1. Mock Android Log to prevent crashes
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.w(any(), any<Throwable>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        // 2. Default mocks for UseCase
        coEvery { useCase.getUsername() } returns Result.success("PlayerOne")
        coEvery { useCase.subscribeToMatch(any()) } returns eventsFlow
        coEvery { useCase.rollAllDices(any()) } returns Result.success(Unit)
        coEvery { useCase.rollDices(any(), any()) } returns Result.success(Unit)
        coEvery { useCase.holdDices(any()) } returns Result.success(Unit)
        coEvery { useCase.startRound(any()) } returns Result.success(Unit)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `init_subscribes_to_match_and_updates_state`() = runTest {
        val sut = MatchViewModel(useCase)
        val states = mutableListOf<MatchState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        sut.inititializeVM("M1")

        // Act: Simulate receiving "Subscribed" event from SSE
        val game = createGame()
        eventsFlow.emit(MatchEvents.Subscribed(game))

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals("PlayerOne", finalState.game?.username)
        assertEquals("turn", finalState.currTurn?.state)
    }

    @Test
    fun `onClickPlay_rolls_all_dices_when_3_rerolls_left`() = runTest {
        val sut = MatchViewModel(useCase)
        sut.inititializeVM("M1")

        // State: It's my turn, fresh start (3 rerolls left)
        val game = createGame(rerolls = 3)
        eventsFlow.emit(MatchEvents.Subscribed(game))

        // Act
        sut.onClickPlay()

        // Assert: First roll is always "Roll All"
        coVerify(exactly = 1) { useCase.rollAllDices("M1") }
    }

    @Test
    fun `onClickPlay_rolls_specific_dices_when_dice_selected`() = runTest {
        val sut = MatchViewModel(useCase)
        sut.inititializeVM("M1")

        // State: Mid-turn (2 rerolls left)
        val game = createGame(rerolls = 2)
        eventsFlow.emit(MatchEvents.Subscribed(game))

        // Act:
        // 1. Select index 0 (visual toggle)
        sut.onClickDice(0)
        // 2. Select index 2 (visual toggle)
        sut.onClickDice(2)

        // Verify visual state updated locally BEFORE network call
        assertTrue(sut.state.value.currHand.value[0].isSelected)
        assertTrue(sut.state.value.currHand.value[2].isSelected)

        // 3. Click Play
        sut.onClickPlay()

        // Assert:
        // Logic sends INDICES TO HOLD. Since we selected 0 and 2,
        // the ViewModel converts these to 1-based indices (1 and 3) for the API.
        coVerify(exactly = 1) {
            useCase.rollDices("M1", listOf(1, 3))
        }
    }

    @Test
    fun `onClickPlay_holds_all_dices_when_0_rerolls_left`() = runTest {
        val sut = MatchViewModel(useCase)
        sut.inititializeVM("M1")

        // State: End of turn (0 rerolls left)
        val game = createGame(rerolls = 0)
        eventsFlow.emit(MatchEvents.Subscribed(game))

        // Act
        sut.onClickPlay()

        // Assert: Must call Hold to finish turn
        coVerify(exactly = 1) { useCase.holdDices("M1") }
    }

    @Test
    fun `RoundComplete_event_triggers_auto_next_timer`() = runTest {
        val sut = MatchViewModel(useCase)
        sut.inititializeVM("M1")

        // Setup: Receive "Round Complete" event
        val game = createGame().copy(msg = "Round Over")
        eventsFlow.emit(MatchEvents.RoundComplete(game))

        // At t=0, startRound should NOT have been called yet (waiting for timer)
        coVerify(exactly = 0) { useCase.startRound(any()) }

        // Act: Advance virtual time by 5 seconds + 1ms
        advanceTimeBy(5001)

        // Assert: The 5s timer finished and triggered startRound automatically
        coVerify(exactly = 1) { useCase.startRound("M1") }
    }
}