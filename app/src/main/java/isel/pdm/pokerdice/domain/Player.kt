package isel.pdm.pokerdice.domain

import java.util.UUID

data class Player(
    val id: UUID,
    val user: User,
    val lobbyId: UUID,
)

typealias Players = MutableList<Player>