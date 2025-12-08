package isel.pdm.pokerdice.services

import isel.pdm.pokerdice.domain.user.UserState
import isel.pdm.pokerdice.services.events.LobbiesEvents
import isel.pdm.pokerdice.services.events.LobbyEvent
import kotlinx.coroutines.flow.Flow

interface LobbyService {
    fun subscribeToLobbies(): Flow<LobbiesEvents>

    fun subscribeToLobby(lobbyId: String): Flow<LobbyEvent>
    suspend fun createLobby(
        authToken: String,
        name: String,
        description: String,
        players: Int,
        rounds: Int,
        balance: Int,
        ante: Int
    ): String?

    suspend fun joinLobby(lobbyId: String, token: String): String?
    suspend fun leaveLobby(lobbyId: String, token: String): String?
    suspend fun checkUserState(token: String): UserState?
}
