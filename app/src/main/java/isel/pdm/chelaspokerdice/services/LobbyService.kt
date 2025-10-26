package isel.pdm.chelaspokerdice.services

import isel.pdm.chelaspokerdice.services.dto.Lobby
import kotlinx.coroutines.flow.Flow

typealias Lobbies = List<Lobby>
interface LobbyService {
    fun getLobbies(): Flow<Lobbies>
    fun searchLobbies(search: String): Flow<Lobbies>

    fun addLobby(newLobby: Lobby)
}