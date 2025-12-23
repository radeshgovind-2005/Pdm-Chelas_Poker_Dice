package isel.pdm.pokerdice.domain.usecases

import isel.pdm.pokerdice.domain.events.LobbiesEvents
import isel.pdm.pokerdice.domain.services.LobbyService
import kotlinx.coroutines.flow.Flow

class BrowseUseCase(
    private val lobbyService: LobbyService
) {

     fun subscribeToLobbies(): Flow<LobbiesEvents>  =
        lobbyService.subscribeToLobbies()
}