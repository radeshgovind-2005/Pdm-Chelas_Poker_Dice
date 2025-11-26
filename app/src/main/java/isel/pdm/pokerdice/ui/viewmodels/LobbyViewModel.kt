package isel.pdm.pokerdice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import isel.pdm.pokerdice.NotificationLog
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.services.LobbyServices
import isel.pdm.pokerdice.services.fake.FakeLobbyService
import isel.pdm.pokerdice.ui.notifications.NotificationSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID

class LobbyViewModel(
    private val services: LobbyServices = FakeLobbyService(),
    private val notificationSource: NotificationSource
) : BaseViewModel<LobbyViewModel.State>() {

    fun startPolling() {
        viewModelScope.launch {
            while (isActive) {

                NotificationLog.logDebug("startPollling")
                //val result = service.getLobby(lobbyId)

                // LOGIC: If we detect the game started, notify the user
                //if (result.isSuccess && result.getOrNull()?.status == "STARTED") {
                notificationSource.showGameStartedNotification( "Game")
                //}

                delay(3000) // Poll every 3 seconds
            }
        }
    }

    companion object {
        fun getFactory(service: LobbyServices,notificationSource: NotificationSource) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LobbyViewModel(service, notificationSource) as T
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

    fun joinLobby(uuid: UUID, user: User) {
        launch(
            onError = { State.Error(it) }
        ) {
            updateState(State.LoadingLobby)
            services
                .getAndJoinOnLobby(uuid,user)
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
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    updateState(State.LobbiesLoaded(data))
                }
        }
    }

    fun leaveLobby(user: User) {
        if(state.value !is State.LobbyLoaded) return
        launch(onError = { State.Error(it) }) {
            val lobby = (state.value as State.LobbyLoaded).lobby
            updateState(State.LeavingLobby)
            services
                .leaveLobby(user,lobby)
                .collect { updateState(State.Idle) }
        }
    }

    fun getCurrentLobby(user: User): Lobby? {
        var lobby: Lobby? = null
        launch(onError = { State.Error(it) }) {
            updateState(State.LoadingLobby)
            services
                .getUserLobby(user)
                .collect { currLobby ->
                    lobby = currLobby
                }
            lobby
                ?.let{updateState(State.LobbyLoaded(it))}
                ?: updateState(State.Error(Exception("Lobby is Null")))
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