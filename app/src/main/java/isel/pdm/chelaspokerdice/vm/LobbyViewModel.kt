package isel.pdm.chelaspokerdice.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.fakeservice.FakeLobbyService
import isel.pdm.chelaspokerdice.services.model.Lobby
import isel.pdm.chelaspokerdice.services.model.types.Description
import isel.pdm.chelaspokerdice.services.model.types.ExpectedPlayers
import isel.pdm.chelaspokerdice.services.model.types.Name
import isel.pdm.chelaspokerdice.services.model.types.NumberOfRounds
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LobbyViewModel(
    private val lobbyService: LobbyService = FakeLobbyService()
): ViewModel() {

    companion object {
        fun getFactory(service: LobbyService) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(LobbyViewModel::class.java)) {
                    LobbyViewModel(lobbyService = service) as T
                }
                else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    // MOVE ESTAS PROPRIEDADES PARA ANTES DO INIT BLOCK
    var state: State by mutableStateOf(State.Idle)
        private set

    var createLobbyState: CreateLobbyState by mutableStateOf(CreateLobbyState.Idle)
        private set

    private var lobbyCollectionJob: Job? = null
    private var searchJob: Job? = null

    init {
        startLobbyCollection()
    }

    // ADD THIS PUBLIC METHOD
    fun getLobbies() {
        startLobbyCollection()
    }

    private fun startLobbyCollection() {
        // Cancel both jobs to avoid conflicts
        lobbyCollectionJob?.cancel()
        searchJob?.cancel()

        lobbyCollectionJob = viewModelScope.launch {
            state = State.Loading
            try {
                lobbyService.getLobbies().collect { lobbies ->
                    state = State.Success(lobbies)
                }
            } catch (e: Exception) {
                state = State.Error(e)
                // Log the error for debugging
                e.printStackTrace()
            }
        }
    }

    fun searchLobbies(query: String) {
        // Cancel both jobs to avoid conflicts
        lobbyCollectionJob?.cancel()
        searchJob?.cancel()

        if (query.isBlank()) {
            startLobbyCollection() // Return to normal lobby list
            return
        }

        searchJob = viewModelScope.launch {
            state = State.Loading
            try {
                lobbyService.searchLobbies(query).collect { lobbies ->
                    state = State.Success(lobbies)
                }
            } catch (e: Exception) {
                state = State.Error(e)
                e.printStackTrace()
            }
        }
    }

    fun createLobby(
        name: String,
        description: String,
        expectedPlayers: String,
        numberOfRounds: String
    ) {
        viewModelScope.launch {
            createLobbyState = CreateLobbyState.Validating

            // Step 1: Validate individual fields
            val nameResult = Name.create(name)
            val descriptionResult = Description.create(description)
            val expectedPlayersInt = expectedPlayers.toIntOrNull()
            val numberOfRoundsInt = numberOfRounds.toIntOrNull()

            // Validate ExpectedPlayers
            val expectedPlayersResult = if (expectedPlayersInt != null) {
                ExpectedPlayers.create(expectedPlayersInt)
            } else {
                Result.failure(IllegalArgumentException("Expected players must be a valid number"))
            }

            // Validate NumberOfRounds (depends on ExpectedPlayers)
            val numberOfRoundsResult = if (numberOfRoundsInt != null && expectedPlayersResult.isSuccess) {
                NumberOfRounds.create(numberOfRoundsInt, expectedPlayersResult.getOrThrow())
            } else if (numberOfRoundsInt == null) {
                Result.failure(IllegalArgumentException("Number of rounds must be a valid number"))
            } else {
                Result.failure(IllegalArgumentException("Cannot validate rounds without valid expected players"))
            }

            // Check if any validation failed
            val validationErrors = listOf(
                nameResult,
                descriptionResult,
                expectedPlayersResult,
                numberOfRoundsResult
            ).mapNotNull { it.exceptionOrNull()?.message }

            if (validationErrors.isNotEmpty()) {
                createLobbyState = CreateLobbyState.ValidationError(validationErrors)
                return@launch
            }

            // Step 2: All validations passed, create lobby
            createLobbyState = CreateLobbyState.Creating

            try {
                // Extract validated values
                val validatedName = nameResult.getOrThrow()
                val validatedDescription = descriptionResult.getOrThrow()
                val validatedExpectedPlayers = expectedPlayersResult.getOrThrow()
                val validatedNumberOfRounds = numberOfRoundsResult.getOrThrow()

                // Call service with validated domain types
                val newLobby = lobbyService.createLobby(
                    name = validatedName.value,
                    description = validatedDescription.value,
                    expectedPlayers = validatedExpectedPlayers.value,
                    nOfRounds = validatedNumberOfRounds.value
                )

                createLobbyState = CreateLobbyState.Success(newLobby)

                // Refresh the lobby list to include the new lobby
                startLobbyCollection()

            } catch (e: Exception) {
                createLobbyState = CreateLobbyState.Error(e)
                e.printStackTrace()
            }
        }
    }

    fun resetCreateLobbyState() {
        createLobbyState = CreateLobbyState.Idle
    }

    fun refreshLobbies() {
        startLobbyCollection()
    }

    fun resetToIdle() {
        lobbyCollectionJob?.cancel()
        searchJob?.cancel()
        state = State.Idle
        createLobbyState = CreateLobbyState.Idle
    }

    override fun onCleared() {
        lobbyCollectionJob?.cancel()
        searchJob?.cancel()
        super.onCleared()
    }

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class Success(val lobbies: List<Lobby>) : State
        data class Error(val exception: Throwable) : State
    }

    sealed interface CreateLobbyState {
        data object Idle : CreateLobbyState
        data object Validating : CreateLobbyState
        data object Creating : CreateLobbyState
        data class ValidationError(val errors: List<String>) : CreateLobbyState
        data class Success(val lobby: Lobby) : CreateLobbyState
        data class Error(val exception: Throwable) : CreateLobbyState
    }
}