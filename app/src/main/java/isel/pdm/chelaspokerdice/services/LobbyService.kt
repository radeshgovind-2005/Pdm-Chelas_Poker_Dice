package isel.pdm.chelaspokerdice.services

import isel.pdm.chelaspokerdice.services.model.Lobby

interface LobbyService {
    suspend fun getLobbies(): List<Lobby>
    suspend fun searchLobbies(): List<Lobby>
    suspend fun createLobby(): Lobby
    suspend fun joinLobby(): Boolean
}