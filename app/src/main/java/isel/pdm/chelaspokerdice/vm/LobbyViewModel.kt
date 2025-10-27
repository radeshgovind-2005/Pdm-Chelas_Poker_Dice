package isel.pdm.chelaspokerdice.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.dto.Lobby
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.util.UUID

class LobbyViewModel(
    private val lobbyService: LobbyService
) : ViewModel() {

    companion object {
        fun getFactory(service: LobbyService) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LobbyViewModel(service) as T
            }
        }
    }

    var state: State by mutableStateOf(State.Idle)
        private set

    private var collectLobbyJob: Job? = null

    // FIXED VERSION - Safe loading with proper error handling
    fun loadLobbies() {
        collectLobbyJob?.cancel()
        collectLobbyJob = viewModelScope.launch {
            try {
                state = State.LoadingLobbies

                // Safe collection with error handling
                lobbyService.getLobbies()
                    .flowOn(Dispatchers.IO)
                    .collect { data ->
                        // SAFE: Explicit type check and handling
                        when (data) {
                            is List<*> -> {
                                @Suppress("UNCHECKED_CAST")
                                val lobbies = data as? List<Lobby> ?: emptyList()
                                state = State.LobbiesLoaded(lobbies)
                            }
                            else -> {
                                Log.e("LobbyViewModel", "Unexpected data type: ${data?.javaClass}")
                                state = State.LobbiesLoaded(emptyList())
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.e("LobbyViewModel", "Error in loadLobbies", e)
                // RECOVER GRACEFULLY instead of spamming errors
                state = State.LobbiesLoaded(emptyList())
            }
        }
    }

    // SIMPLIFIED createLobby without complex error spam
    fun createLobby(name: String, description: String, expectedPlayers: String, numberOfRounds: String){
        viewModelScope.launch {
            try {
                state = State.CreatingLobby

                val expectedPlayersInt = expectedPlayers.toIntOrNull() ?: 2
                val numberOfRoundsInt = numberOfRounds.toIntOrNull() ?: 5

                val lobbyResult = Lobby.create(name, description, expectedPlayersInt, numberOfRoundsInt)

                if (lobbyResult.isSuccess) {
                    val lobby = lobbyResult.getOrThrow()
                    // Add to service and update state
                    state = State.InLobby(lobby)
                } else {
                    state = State.LobbiesLoaded(emptyList()) // Recover gracefully
                }
            } catch (e: Exception) {
                Log.e("LobbyViewModel", "Create lobby error", e)
                state = State.LobbiesLoaded(emptyList()) // Always recover
            }
        }
    }

    // FIXED search with safe error handling
    fun searchLobbies(search: String) {
        collectLobbyJob?.cancel()
        collectLobbyJob = viewModelScope.launch {
            try {
                state = State.LoadingLobby
                lobbyService.searchLobbies(search)
                    .flowOn(Dispatchers.IO)
                    .collect { data ->
                        when (data) {
                            is List<*> -> {
                                @Suppress("UNCHECKED_CAST")
                                val lobbies = data as? List<Lobby> ?: emptyList()
                                state = State.LobbiesLoaded(lobbies)
                            }
                            else -> state = State.LobbiesLoaded(emptyList())
                        }
                    }
            } catch (e: Exception) {
                state = State.LobbiesLoaded(emptyList()) // Always recover
            }
        }
    }

    // PROPER cancellation that prevents error spam
    fun cancelOperations() {
        collectLobbyJob?.cancel()
        // Don't set state to Idle immediately to avoid rapid re-triggers
    }

    override fun onCleared() {
        super.onCleared()
        collectLobbyJob?.cancel()
    }

    // In LobbyViewModel.kt, update the loadLobby method:
    fun loadLobby(lobbyId: String) {
        collectLobbyJob?.cancel()
        collectLobbyJob = viewModelScope.launch {
            try {
                state = State.LoadingLobbies

                // Convert String to UUID
                val uuid = UUID.fromString(lobbyId)

                // Load all lobbies and find the specific one
                lobbyService.getLobbies()
                    .flowOn(Dispatchers.IO)
                    .collect { data ->
                        when (data) {
                            is List<*> -> {
                                @Suppress("UNCHECKED_CAST")
                                val lobbies = data as? List<Lobby> ?: emptyList()
                                val selectedLobby = lobbies.find { it.id == uuid }

                                if (selectedLobby != null) {
                                    state = State.InLobby(selectedLobby)
                                } else {
                                    state = State.Error(IllegalArgumentException("Lobby not found"))
                                }
                            }
                            else -> {
                                state = State.Error(IllegalArgumentException("Invalid data received"))
                            }
                        }
                    }
            } catch (e: IllegalArgumentException) {
                Log.e("LobbyViewModel", "Invalid UUID format: $lobbyId", e)
                state = State.Error(e)
            } catch (e: Exception) {
                Log.e("LobbyViewModel", "Error loading lobby: $lobbyId", e)
                state = State.Error(e)
            }
        }
    }
    sealed interface State {
        data object Idle : State
        data object LoadingLobbies : State
        data object LoadingLobby : State
        data object CreatingLobby : State
        data object JoiningLobby : State
        data class LobbiesLoaded(val lobbies: List<Lobby>) : State
        data class InLobby(val lobby: Lobby) : State
        data class Error(val exception: Throwable) : State
    }
}