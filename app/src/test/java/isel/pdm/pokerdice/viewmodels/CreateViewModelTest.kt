package isel.pdm.pokerdice.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.usecases.CreateUseCase
import isel.pdm.pokerdice.ui.viewmodels.create.CreateNavigation
import isel.pdm.pokerdice.ui.viewmodels.create.CreateState
import isel.pdm.pokerdice.ui.viewmodels.create.CreateViewModel
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<CreateUseCase>()

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

    // Helper to fill form with valid data
    private fun fillValidForm(sut: CreateViewModel) {
        sut.onNameChange("Table1") // Valid: Capital start
        sut.onDescriptionChange("Fun game") // Valid: Capital start
        sut.onExpectedPlayersChange(2) // Valid: 2..6
        sut.onMaxRoundsChange(4) // Valid: Multiple of 2
        sut.onBalanceChange(100) // Valid: > 0
        sut.onAnteChange(10) // Valid: < Balance
    }


    @Test
    fun `validation_fails_for_invalid_name_format`() = runTest {
        val sut = CreateViewModel(SavedStateHandle(), useCase)

        // Invalid: Starts with lowercase
        sut.onNameChange("lowerCaseName")

        assertNotNull(sut.state.value.nameError)
        assertEquals("Must start with a Capital letter.", sut.state.value.nameError)
        assertFalse(sut.state.value.isCreateEnabled)
    }

    @Test
    fun `validation_fails_if_rounds_is_not_multiple_of_players`() = runTest {
        val sut = CreateViewModel(SavedStateHandle(), useCase)

        sut.onExpectedPlayersChange(3)
        sut.onMaxRoundsChange(4) // 4 is not multiple of 3

        assertNotNull(sut.state.value.maxRoundsError)
        assertTrue(sut.state.value.maxRoundsError!!.contains("multiple"))
        assertFalse(sut.state.value.isCreateEnabled)
    }

    @Test
    fun `validation_fails_if_ante_is_greater_than_balance`() = runTest {
        val sut = CreateViewModel(SavedStateHandle(), useCase)

        sut.onBalanceChange(50)
        sut.onAnteChange(100) // Ante > Balance

        assertNotNull(sut.state.value.anteError)
        assertEquals("Ante must be less than Balance", sut.state.value.anteError)
        assertFalse(sut.state.value.isCreateEnabled)
    }

    @Test
    fun `createLobby_success_flow_navigates_to_Lobby`() = runTest {
        // Arrange
        val sut = CreateViewModel(SavedStateHandle(), useCase)
        val newLobbyId = "lobby_123"

        coEvery {
            useCase.createLobby(
                name = "Table1",
                description = "Fun game",
                players = 2,
                rounds = 4,
                balance = 100,
                ante = 10
            )
        } returns Result.success(newLobbyId)

        val effects = mutableListOf<CreateNavigation>()
        val states = mutableListOf<CreateState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        fillValidForm(sut)

        // Pre-check: Form must be valid now
        assertTrue("Form should be valid", sut.state.value.isCreateEnabled)

        sut.onCreateLobby()

        // Assert
        // 1. Loading state
        assertTrue(states.any { it.isLoading })

        // 2. Final state
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertNull(finalState.error)

        // 3. Navigation
        assertEquals(1, effects.size)
        val nav = effects.first() as CreateNavigation.ToLobby
        assertEquals(newLobbyId, nav.lobbyId)
    }

    @Test
    fun `createLobby_failure_shows_error`() = runTest {
        // Arrange
        val sut = CreateViewModel(SavedStateHandle(), useCase)
        val errorMsg = "Network Timeout"

        coEvery {
            useCase.createLobby(any(), any(), any(), any(), any(), any())
        } returns Result.failure(Exception(errorMsg))

        val states = mutableListOf<CreateState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        fillValidForm(sut)
        sut.onCreateLobby()

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals(errorMsg, finalState.error)
    }
}