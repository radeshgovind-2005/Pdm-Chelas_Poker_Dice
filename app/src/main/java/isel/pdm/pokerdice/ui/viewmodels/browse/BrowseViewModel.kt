package isel.pdm.pokerdice.ui.viewmodels.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.lobby.BrowseLobby
import isel.pdm.pokerdice.services.events.LobbiesEvents
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.usecases.BrowseUseCase

class BrowseViewModel(
    private val usecase: BrowseUseCase
) : BaseViewModel<BrowseState, BrowseNavigation>(BrowseState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: BrowseUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
                    BrowseViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    init{
        logger.i("VM init")
        launchWithHandler {
            subscribeToLobbies()
        }
        logger.i("VM initialized successfully")
    }

    private suspend fun subscribeToLobbies(){
        logger.i("subscribe to Lobbies")
        setState { copy(isLoading=true) }
        usecase
            .subscribeToLobbies()
            .collect { event->
                logger.i("Event received: $event")
                when(event){
                    is LobbiesEvents.Init -> {
                        setState {
                            copy(
                                lobbies = event.lobbies,
                                filteredLobbies = filterLobbies(event.lobbies, query),
                                isLoading = false
                            )
                        }
                    }

                    is LobbiesEvents.Add -> {
                        val newLobbies = state.value.lobbies + event.lobby
                        setState {
                            copy(
                                lobbies = newLobbies,
                                filteredLobbies = filterLobbies(newLobbies, query),
                                isLoading = false
                            )
                        }
                    }
                    is LobbiesEvents.Remove -> {
                        val newLobbies = state.value.lobbies.filter{it.id != event.lobbyId}
                        setState {
                            copy(
                                lobbies = newLobbies,
                                filteredLobbies = filterLobbies(newLobbies, query),
                                isLoading = false
                            )
                        }
                    }
                    is LobbiesEvents.Error -> {
                        logger.i("Lobbies  Event message: ${event.message}")
                        setState {
                            copy(isLoading=false, error=event.message)
                        }
                    }
                    LobbiesEvents.KeepAlive ->  logger.i("Lobbies  Event message: Keep Alive")
                    is LobbiesEvents.Connected -> {
                        logger.i("Lobby events Connected")
                    }
                }

            }

    }
    fun onBackRequest() {
        logger.i("Back Requested -> Navigate to Browse Activity")
        sendEffect(BrowseNavigation.ToTitle)
    }
    fun onCreateRequest() {
        logger.i("Create Requested -> Navigate to Create Activity")
        sendEffect(BrowseNavigation.ToCreateLobby)
    }

    fun onLobbyRequest(lobbyId: String) {
        logger.i("Lobby Requested -> Navigate to Lobby Activity")
        sendEffect(BrowseNavigation.ToLobby(lobbyId))
    }

    fun onChangeExpand() {
        val value=!state.value.expanded
        logger.v("Expand changed: $value")
       // setState { copy(expanded=value) }
    }

    fun onQueryChange(query: String){
        logger.v("Query changed: $query")
        setState {
            copy(
                query=query,
                filteredLobbies = filterLobbies(this.lobbies, query)
            )
        }
    }

    private fun filterLobbies(lobbies: List<BrowseLobby>, query: String): List<BrowseLobby> {
        if (query.isBlank()) return lobbies

        return lobbies.filter { lobby ->
            lobby.name.contains(query, ignoreCase = true) ||
                    lobby.hostName.contains(query, ignoreCase = true)
        }
    }

}