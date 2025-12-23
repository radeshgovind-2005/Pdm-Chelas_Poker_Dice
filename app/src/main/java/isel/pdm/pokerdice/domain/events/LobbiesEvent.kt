package isel.pdm.pokerdice.domain.events

import isel.pdm.pokerdice.domain.model.lobby.BrowseLobby

sealed class LobbiesEvents {
    data class Init(val lobbies: List<BrowseLobby>) : LobbiesEvents()
    data class Connected(val message: String) : LobbiesEvents()
    data class Remove(val lobbyId: String) : LobbiesEvents()
    data class Add(val lobby: BrowseLobby) : LobbiesEvents()
    data class Error(val message: String) : LobbiesEvents()
    data object KeepAlive : LobbiesEvents()
}