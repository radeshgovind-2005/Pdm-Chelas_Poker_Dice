package isel.pdm.chelaspokerdice.services.fakeservice

import isel.pdm.chelaspokerdice.services.Lobbies
import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.dto.Lobby
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeLobbyService : LobbyService {

    private val allLobbies = MutableStateFlow<Lobbies>(emptyList())


    init{
        generateFakeLobbies()
    }

    override fun getLobbies(): Flow<Lobbies> = allLobbies

    override fun searchLobbies(search: String): Flow<Lobbies> =
        allLobbies.map { l ->
            l.filter { it.name.value.contains(search, true) }
        }

    override fun addLobby(newLobby: Lobby) {
        val currentLobbies = allLobbies.value.toMutableList()
        currentLobbies.add(newLobby)
        allLobbies.value = currentLobbies
    }

    private fun generateFakeLobbies(){
        val fakeLobbies = listOf(
            Lobby.create(
                name = "Poker Masters",
                description = "Competitive poker tournament",
                expectedPlayers = 4,
                nOfRounds = 20
            ).getOrThrow(),
            Lobby.create(
                name = "Casual Friday",
                description = "Friendly Friday games",
                expectedPlayers = 3,
                nOfRounds = 15
            ).getOrThrow(),
            Lobby.create(
                name = "High Rollers",
                description = "For experienced players",
                expectedPlayers = 6,
                nOfRounds = 30
            ).getOrThrow(),
            Lobby.create(
                name = "Beginner's Luck",
                description = "New players welcome!",
                expectedPlayers = 2,
                nOfRounds = 10
            ).getOrThrow(),
            Lobby.create(
                name = "Poker Masters",
                description = "Competitive poker tournament",
                expectedPlayers = 4,
                nOfRounds = 20
            ).getOrThrow(),
            Lobby.create(
                name = "Casual Friday",
                description = "Friendly Friday games",
                expectedPlayers = 3,
                nOfRounds = 15
            ).getOrThrow(),
            Lobby.create(
                name = "High Rollers",
                description = "For experienced players",
                expectedPlayers = 6,
                nOfRounds = 30
            ).getOrThrow(),
            Lobby.create(
                name = "Beginner's Luck",
                description = "New players welcome!",
                expectedPlayers = 2,
                nOfRounds = 10
            ).getOrThrow()

        )

        allLobbies.value = fakeLobbies
    }

}

