package isel.pdm.pokerdice.ui.viewmodels.usecases

import isel.pdm.pokerdice.services.LobbyService
import isel.pdm.pokerdice.services.events.LobbiesEvents
import kotlinx.coroutines.flow.Flow

class BrowseUseCase(
    private val lobbyService: LobbyService
) {

     fun subscribeToLobbies(): Flow<LobbiesEvents>  =
        lobbyService.subscribeToLobbies()
}