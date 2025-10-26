package isel.pdm.chelaspokerdice.services.fakeservice

import isel.pdm.chelaspokerdice.services.LobbyService
import isel.pdm.chelaspokerdice.services.model.Lobby
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID
import kotlin.random.Random

class FakeLobbyService : LobbyService {

    private val _allLobbies = MutableStateFlow<List<Lobby>>(emptyList())
    private val _lobbyUpdates = mutableMapOf<UUID, MutableStateFlow<Lobby>>()

    init {
        generateInitialLobbies()
    }

    override fun getLobbies(): Flow<List<Lobby>> = _allLobbies


    override fun searchLobbies(byName: String): Flow<List<Lobby>> =
        _allLobbies.map { lobbiesWithId ->
            lobbiesWithId
                .filter { it.name.value.contains(byName, ignoreCase = true) }
        }

    override fun getLobbyUpdates(lobbyId: UUID): Flow<Lobby> {
        return _lobbyUpdates.getOrPut(lobbyId) {
            MutableStateFlow(
                _allLobbies.value.find { it.id == lobbyId } ?: createRandomLobby()
            )
        }.asStateFlow()
    }

    override suspend fun createLobby(
        name: String,
        description: String,
        expectedPlayers: Int,
        nOfRounds: Int
    ): Lobby {
        delay(500) // Simulate network delay

        val lobbyResult = Lobby.create(name, description, expectedPlayers, nOfRounds)
        val newLobby = lobbyResult.getOrThrow()
        val newLobbyId = UUID.randomUUID()

        _allLobbies.update { currentLobbies ->
            currentLobbies + newLobby
        }

        // Create update flow for the new lobby
        _lobbyUpdates[newLobbyId] = MutableStateFlow(newLobby)

        return newLobby
    }

    override suspend fun joinLobby(lobbyId: UUID): Boolean {
        delay(300) // Simulate network delay

        // Check if lobby exists
        val lobbyExists = _allLobbies.value.any { it.id == lobbyId }
        if (!lobbyExists) return false

        // Randomly fail sometimes to simulate real-world scenarios
        return Random.nextDouble() > 0.2 // 80% success rate
    }

    override suspend fun leaveLobby(lobbyId: UUID): Boolean {
        delay(200) // Simulate network delay

        // Check if lobby exists
        val lobbyExists = _allLobbies.value.any { it.id == lobbyId }
        return lobbyExists // Return true if lobby exists, false otherwise
    }


    private fun generateInitialLobbies() {
        val initialLobbies = listOf(

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

        _allLobbies.value = initialLobbies

        // Initialize update flows for initial lobbies
        initialLobbies.forEach { lobby ->
            _lobbyUpdates[lobby.id] = MutableStateFlow(lobby)
        }
    }

    private fun createRandomLobby(): Lobby {
        val names = listOf(
            "Quick Match", "Weekend Warriors", "Dice Champions", "Poker Night",
            "Strategy Session", "Lucky Roll", "Royal Flush Club", "Bluff Masters"
        )

        val descriptions = listOf(
            "Fast-paced games", "Relaxed weekend play", "Competitive environment",
            "Friendly matches", "Learn and play", "High stakes action", "Casual fun"
        )

        val randomPlayers = Random.nextInt(2, 7)
        val randomRounds = randomPlayers * Random.nextInt(1, 11) // Multiple of players, up to 10x

        return Lobby.create(
            name = "${names.random()} ${Random.nextInt(100, 1000)}",
            description = descriptions.random(),
            expectedPlayers = randomPlayers,
            nOfRounds = randomRounds
        ).getOrThrow()
    }

}