package isel.pdm.pokerdice.domain

import isel.pdm.pokerdice.domain.values.Description
import isel.pdm.pokerdice.domain.values.ExpectedPlayers
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.domain.values.NumberOfRounds
import java.util.UUID
import kotlin.collections.mutableListOf

typealias Lobbies = List<Lobby>

data class Lobby(
    val id: UUID,
    val name: Name,
    val description: Description,
    val expectedPlayers: ExpectedPlayers,
    val nOfRounds: NumberOfRounds,
    val ante: Int = 10,
    val hostName: Name,
    val lobbyUsers: Users,
    val turn: User?
) {
    companion object {
        fun create(
            name: String,
            description: String,
            expectedPlayers: Int,
            nOfRounds: Int,
            host: User,
        ): Result<Lobby> {
            return runCatching {
                val nameResult = Name.create(name)
                val descriptionResult = Description.create(description)
                val expectedPlayersResult = ExpectedPlayers.create(expectedPlayers)
                val expectedPlayersValue = expectedPlayersResult.getOrThrow()
                val nOfRoundsResult = NumberOfRounds.create(nOfRounds, expectedPlayersValue.value)
                val lobbyId = UUID.randomUUID()
                Lobby(
                    id = lobbyId,
                    name = nameResult.getOrThrow(),
                    description = descriptionResult.getOrThrow(),
                    expectedPlayers = expectedPlayersValue,
                    nOfRounds = nOfRoundsResult.getOrThrow(),
                    hostName = host.authInfo.userName,
                    lobbyUsers = mutableListOf(host),
                    turn= host
                )
            }
        }
    }
}