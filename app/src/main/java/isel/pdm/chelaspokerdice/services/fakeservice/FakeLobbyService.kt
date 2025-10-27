package isel.pdm.chelaspokerdice.services.fakeservice

import isel.pdm.chelaspokerdice.services.Lobbies
import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.dto.Lobby
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FakeLobbyService : LobbyService {

    private val allLobbies = MutableStateFlow<Lobbies>(emptyList())


    init{
        generateFakeLobbies()
        println("FakeLobbyService initialized - instance: ${this.hashCode()}")
    }

    override fun getLobbies(): Flow<Lobbies> = flow {
        // Simulate network delay but don't block
        delay(1000) // Small delay for realism
        emit(allLobbies.value)
    }.flowOn(Dispatchers.IO)

    override fun searchLobbies(search: String): Flow<Lobbies> =
        allLobbies.map { l ->
            l.filter { it.name.value.contains(search, true) }
        }

    override fun addLobby(newLobby: Lobby) {
        val currentLobbies = allLobbies.value.toMutableList()
        println("BEFORE addLobby - Total lobbies: ${currentLobbies.size}")
        println("Adding lobby: ${newLobby.id} - ${newLobby.name.value}")
        currentLobbies.add(newLobby)
        allLobbies.value = currentLobbies

        println("AFTER addLobby - Total lobbies: ${allLobbies.value.size}")
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