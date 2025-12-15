package isel.pdm.pokerdice.services.fake

import isel.pdm.pokerdice.domain.lobby.BrowseLobby
import isel.pdm.pokerdice.domain.lobby.Lobby
import isel.pdm.pokerdice.domain.user.UserState
import isel.pdm.pokerdice.services.LobbyService
import isel.pdm.pokerdice.services.events.LobbiesEvents
import isel.pdm.pokerdice.services.events.LobbyEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class FakeLobbyService : LobbyService {

    // 1. Base de dados em memória (ID -> Lobby Completo)
    private val lobbies = mutableMapOf<String, Lobby>()

    // 2. Popular com dados falsos ao iniciar
    init {
        createFakeLobby("Lobby do Radesh", "Radesh", "waiting", 4)
        createFakeLobby("Mesa dos Pros", "Alice", "waiting", 2)
        createFakeLobby("Jogo Rápido", "Bob", "playing", 3)
    }

    // Função auxiliar para criar lobbies falsos
    private fun createFakeLobby(name: String, host: String, state: String, maxPlayers: Int) {
        val id = UUID.randomUUID().toString()
        lobbies[id] = Lobby(
            id = id,
            name = name,
            description = "Descrição automática para testes.",
            host = host,
            state = state,
            maxRounds = 5,
            maxPlayers = maxPlayers,
            ante = 100,
            initialBalance = 1000,
            players = listOf(host) // O host já está na lista
        )
    }

    override fun subscribeToLobbies(): Flow<LobbiesEvents> = flow {
        // Simular conexão
        emit(LobbiesEvents.Connected("Fake connection established"))
        delay(500)

        // Converter os lobbies completos para BrowseLobby (que é o que a lista pede)
        val browseList = lobbies.values.map {
            BrowseLobby(
                id = it.id,
                name = it.name,
                rounds = it.maxRounds,
                hostName = it.host
            )
        }

        // Emitir a lista inicial
        emit(LobbiesEvents.Init(browseList))

        // Manter o flow vivo (Heartbeat)
        while (true) {
            delay(5000)
            emit(LobbiesEvents.KeepAlive)
        }
    }

    override fun subscribeToLobby(lobbyId: String): Flow<LobbyEvent> = flow {
        val lobby = lobbies[lobbyId]

        if (lobby == null) {
            emit(LobbyEvent.Error("Lobby not found"))
            return@flow
        }

        emit(LobbyEvent.Connected("Connected to lobby ${lobby.name}"))
        delay(200)

        // Emitir estado atual do Lobby
        emit(LobbyEvent.CurrentLobby(lobby))

        // Loop para manter a conexão e simular eventos (opcional)
        while (true) {
            delay(3000)
            emit(LobbyEvent.KeepAlive)
        }
    }

    override suspend fun createLobby(
        authToken: String,
        name: String,
        description: String,
        players: Int,
        rounds: Int,
        balance: Int,
        ante: Int
    ): String? {
        delay(1000) // Simular delay de rede

        val id = UUID.randomUUID().toString()
        // Assumimos que o criador é "Me" para testes, ou extraímos do token se quisermos ser rigorosos
        val hostName = "Me"

        val newLobby = Lobby(
            id = id,
            name = name,
            description = description,
            host = hostName,
            state = "waiting",
            maxRounds = rounds,
            maxPlayers = players,
            ante = ante,
            initialBalance = balance,
            players = listOf(hostName)
        )

        lobbies[id] = newLobby
        return id
    }

    override suspend fun joinLobby(lobbyId: String, token: String): String? {
        delay(800)
        val lobby = lobbies[lobbyId] ?: throw Exception("Lobby not found")

        if (lobby.players.size >= lobby.maxPlayers) {
            throw Exception("Lobby full") // Simula erro 409
        }

        // Adicionar o jogador "Me" à lista
        val updatedPlayers = lobby.players + "Me"
        lobbies[lobbyId] = lobby.copy(players = updatedPlayers)

        return lobbyId
    }

    override suspend fun leaveLobby(lobbyId: String, token: String): String? {
        delay(500)
        val lobby = lobbies[lobbyId] ?: throw Exception("Lobby not found")

        // Remover o jogador "Me"
        val updatedPlayers = lobby.players - "Me"

        if (updatedPlayers.isEmpty()) {
            lobbies.remove(lobbyId) // Se ficar vazio, apaga o lobby
        } else {
            lobbies[lobbyId] = lobby.copy(players = updatedPlayers)
        }

        return lobbyId
    }

    override suspend fun checkUserState(token: String): UserState? {
        delay(600)

        // Lógica Falsa Inteligente:
        // Procura em todos os lobbies se o jogador "Me" está lá dentro.
        // Se estiver, retorna que o UserState é "waiting" nesse lobby.

        val lobbyWithPlayer = lobbies.values.find { it.players.contains("Me") }

        return if (lobbyWithPlayer != null) {
            // Se o estado do lobby for "playing", o user state também deve ser
            UserState(lobbyWithPlayer.state, lobbyWithPlayer.id)
        } else {
            null // User está livre
        }
    }
}