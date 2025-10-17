package isel.pdm.chelaspokerdice.services.model.fakeservice

import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.model.Lobby
import kotlinx.coroutines.delay

class FakeLobbyService: LobbyService {
    override suspend fun getLobbies(): List<Lobby> {
        delay(1000) // Simulate Network Delay
        return listOf(
            Lobby("Lobby of Cristiano"),
            Lobby("Lobby of Ronaldo"),
            Lobby("Lobby of Aveiro"),
            Lobby("Lobby of Dos"),
            Lobby("Lobby of Santos")
        )
    }

    override suspend fun searchLobbies(): List<Lobby> {
        TODO("Not yet implemented")
    }

    override suspend fun createLobby(): Lobby {
        TODO("Not yet implemented")
    }

    override suspend fun joinLobby(): Boolean {
        TODO("Not yet implemented")
    }
}