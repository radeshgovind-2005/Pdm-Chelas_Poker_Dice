package isel.pdm.chelaspokerdice.services.dto

import isel.pdm.chelaspokerdice.services.dto.types.Description
import isel.pdm.chelaspokerdice.services.dto.types.ExpectedPlayers
import isel.pdm.chelaspokerdice.services.dto.types.Name
import isel.pdm.chelaspokerdice.services.dto.types.NumberOfRounds
import java.util.UUID

data class Lobby(
    val id: UUID,
    val name: Name,
    val description: Description,
    val expectedPlayers: ExpectedPlayers,
    val nOfRounds: NumberOfRounds,
    val hostName: String = "Radesh",
    val lobbyPlayers: MutableList<String> = mutableListOf<String>()
) {
    companion object {
        fun create(
            name: String,
            description: String,
            expectedPlayers: Int,
            nOfRounds: Int
        ): Result<Lobby> {
            return runCatching {
                val nameResult = Name.create(name)
                val descriptionResult = Description.create(description)
                val expectedPlayersResult = ExpectedPlayers.create(expectedPlayers)
                val expectedPlayersValue = expectedPlayersResult.getOrThrow()
                val nOfRoundsResult = NumberOfRounds.create(nOfRounds, expectedPlayersValue)
                Lobby(
                    id = UUID.randomUUID(),
                    name = nameResult.getOrThrow(),
                    description = descriptionResult.getOrThrow(),
                    expectedPlayers = expectedPlayersValue,
                    nOfRounds = nOfRoundsResult.getOrThrow()
                )
            }
        }
    }
}