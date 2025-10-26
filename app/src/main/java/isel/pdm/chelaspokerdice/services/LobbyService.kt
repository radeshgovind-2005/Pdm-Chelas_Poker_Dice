package isel.pdm.chelaspokerdice.services

import isel.pdm.chelaspokerdice.services.model.Lobby
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LobbyService {
    /**
     * Get real-time stream of all available lobbies
     */
    fun getLobbies(): Flow<List<Lobby>>

    /**
     * Get real-time stream of search results
     */
    fun searchLobbies(byName: String): Flow<List<Lobby>>

    /**
     * Get real-time updates for a specific lobby
     */
    fun getLobbyUpdates(lobbyId: UUID): Flow<Lobby>
    suspend fun createLobby(name: String, description: String, expectedPlayers: Int, nOfRounds: Int): Lobby
    suspend fun joinLobby(lobbyId: UUID): Boolean
    suspend fun leaveLobby(lobbyId: UUID): Boolean
}