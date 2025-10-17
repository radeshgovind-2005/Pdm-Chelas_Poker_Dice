package isel.pdm.chelaspokerdice.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.model.Lobby
import isel.pdm.chelaspokerdice.services.model.fakeservice.FakeLobbyService
import kotlinx.coroutines.launch

class LobbyViewModel(
    private val lobbyService: LobbyService = FakeLobbyService()
): ViewModel() {

    var state: State by mutableStateOf(State.Loading)

    init{
        loadLobbies()
    }

    private fun loadLobbies(){
        viewModelScope.launch {
            try {
                val lobbies = lobbyService.getLobbies()
                state = State.Loaded(lobbies)
            } catch (e: Exception) {
                state = State.Error(e.message ?: "Creating lobbies error")
            }
        }
    }


    sealed class State{
        data object Loading: State()
        data class Loaded(val lobbies: List<Lobby>): State()
        data class Error(val message: String) : State()
    }
}