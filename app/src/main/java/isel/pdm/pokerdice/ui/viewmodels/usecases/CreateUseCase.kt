package isel.pdm.pokerdice.ui.viewmodels.usecases

import isel.pdm.pokerdice.repo.AuthRepository
import isel.pdm.pokerdice.services.LobbyService

class CreateUseCase(
    private val lobbyService: LobbyService,
    private val authRepo: AuthRepository
) {

    suspend fun createLobby(
        name: String,
        description: String,
        players: Int?,
        rounds: Int?,
        balance: Int?,
        ante: Int?
    ): Result<String> =
        runCatching {
            val error = when{
                players==null -> "Players cannot be null"
                rounds==null -> "Rounds cannot be null"
                balance==null -> "Balance cannot be null"
                ante==null -> "Ante cannot be null"
                else -> null
            }
            if(error != null) throw Exception(error)
            val host = authRepo.getAuthInfo()?.authToken ?: throw Exception("Session Expired")
            val lobbyId= lobbyService.createLobby(host,name, description, players!!, rounds!!, balance!!, ante!!)
            if(lobbyId==null) throw Exception("Lobby not created successfully")
            lobbyId
        }
}