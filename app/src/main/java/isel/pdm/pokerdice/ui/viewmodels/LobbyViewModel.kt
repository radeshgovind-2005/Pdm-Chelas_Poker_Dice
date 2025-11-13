package isel.pdm.pokerdice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.services.LobbyServices
import isel.pdm.pokerdice.services.fake.FakeLobbyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class LobbyViewModel(
    private val services: LobbyServices = FakeLobbyService()
) : BaseViewModel<LobbyViewModel.State>() {

    companion object {
        fun getFactory(service: LobbyServices) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LobbyViewModel(service) as T
            }
        }
    }

    override val initialState: State = State.Idle

    fun loadLobbies() {
        launch(
            onError = { State.Error(it) }
        ) {
            updateState(State.LoadingLobbies)
            services
                .getWaitingLobbies()
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    updateState(State.LobbiesLoaded(data))
                }
        }
    }

    fun joinLobby(id: String, user: User) {
        launch(
            onError = { State.Error(it) }
        ) {
            updateState(State.LoadingLobby)
            services
                .getAndJoinOnLobby(id,user)
                .collect { data ->
                    updateState(
                        data?.let { State.LobbyLoaded(it) }
                            ?: State.Error(Exception("Lobby not Found"))
                    )
                }
        }
    }

    fun insertLobby(name: String, description: String, expectedPlayers: String, numberOfRounds: String, host: User) {
        launch(
            onError = { State.Error(it) }
        ) {
            updateState(State.CreatingLobby)
            val expectedPlayersInt = expectedPlayers.toIntOrNull() ?: 2
            val numberOfRoundsInt = numberOfRounds.toIntOrNull() ?: 5
            Lobby
                .create(name, description, expectedPlayersInt, numberOfRoundsInt,host)
                .fold(
                    onSuccess = { newLobby ->
                        services
                            .insertLobby(newLobby)
                            .collect { data ->
                                updateState(
                                    data?.let { State.CreatedLobby(newLobby) }
                                        ?: State.Error(Exception("Lobby not created successfully!"))
                                )
                            }
                    },
                    onFailure = { e ->
                        updateState(State.Error(e))
                    }
                )
        }
    }

    fun searchLobbies(search: String) {
        launch(
            onError = { State.Error(it) }
        ) {
            updateState(State.SearchingLobbies)
            services
                .getLobbies(search)
                .collect { data ->
                    updateState(State.LobbiesLoaded(data))
                }
        }
    }

    fun leaveLobby(user: User) {
        if(state !is State.LobbyLoaded) return
        launch(onError = { State.Error(it) }) {
            val lobby = (state as State.LobbyLoaded).lobby
            updateState(State.LeavingLobby)
            services
                .leaveLobby(user,lobby)
                .collect { updateState(State.Idle) }
        }
    }

    fun getCurrentLobby(user: User): Lobby? {
        var lobby: Lobby? = null
        launch(onError = { LobbyViewModel.State.Error(it) }) {
            services
                .getUserLobby(user)
                .collect { currLobby ->
                    lobby = currLobby
                }
        }
        return lobby
    }

    sealed interface State {
        data object Idle : State
        data class Error(val e: Throwable) : State

        // LOBBIES
        data object LoadingLobbies : State
        data object SearchingLobbies : State
        data class LobbiesLoaded(val lobbies: Lobbies) : State

        // LOBBY
        data object LoadingLobby : State
        data object LeavingLobby : State
        data class LobbyLoaded(val lobby: Lobby) : State


        // CREATE LOBBY
        data object CreatingLobby : State
        data class CreatedLobby(val lobby: Lobby) : State
    }
}