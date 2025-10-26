package isel.pdm.chelaspokerdice.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.fakeservice.FakeLobbyService
import isel.pdm.chelaspokerdice.services.dto.Lobby
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

    //Public values
    var state: State by mutableStateOf(State.Idle)
        private set

    val currentLobby: Lobby?
        get() = (state as? State.InLobby)?.lobby

    //Jobs
    private var collectLobbyJob: Job? =  null


    init{
        loadLobbies()
    }
    private fun loadLobbies(){
        collectLobbyJob?.cancel()

        collectLobbyJob = viewModelScope.launch {
            state = State.LoadingLobbies
            try{
                lobbyService
                    .getLobbies()
                    .collect {  state = State.LobbiesLoaded(it) }
            } catch (e: Exception) {
                state = State.Error(e)
            }
        }
    }
    fun createLobby(name: String, description: String, expectedPlayers: String, numberOfRounds: String, ){
        viewModelScope.launch {
            try {
                state = State.CreatingLobby
                val expectedPlayersInt = expectedPlayers.toIntOrNull()
                    ?: throw IllegalArgumentException("Expected players must be a valid number")
                val numberOfRoundsInt = numberOfRounds.toIntOrNull()
                    ?: throw IllegalArgumentException("Number of rounds must be a valid number")

                val lobbyResult = Lobby.create(name, description, expectedPlayersInt, numberOfRoundsInt)

                if (lobbyResult.isSuccess) {
                    val lobby = lobbyResult.getOrThrow()
                    lobbyService.addLobby(lobby)
                    state = State.InLobby(lobby)
                    loadLobbies()
                } else {
                    state = State.Error(lobbyResult.exceptionOrNull() ?: Exception("Failed to create lobby"))
                }
            }catch (e: Exception){
                state = State.Error(e)
            }
        }
    }
    fun searchLobbies(search: String){
        viewModelScope.launch {
            try {
                state = State.LoadingLobbies
                lobbyService
                    .searchLobbies(search)
                    .collect { state = State.LobbiesLoaded(it) }
            } catch (e: Exception) {
                state = State.Error(e)
            }
        }
    }
    sealed interface State {
        data object Idle : State
        data object LoadingLobbies : State
        data object CreatingLobby : State
        data object JoiningLobby : State
        data class LobbiesLoaded(val lobbies: List<Lobby>) : State
        data class InLobby(val lobby: Lobby) : State
        data class Error(val exception: Throwable) : State
    }

}