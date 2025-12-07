package isel.pdm.pokerdice.services.events

import isel.pdm.pokerdice.domain.match.Match
import isel.pdm.pokerdice.domain.lobby.Lobby

sealed interface LobbyEvent {
    data class CurrentLobby(val lobby: Lobby): LobbyEvent
    data class Connected(val message: String) : LobbyEvent
    data class PlayerJoined(val username: String) : LobbyEvent
    data class PlayerLeft(val username: String) : LobbyEvent
    data class MatchInit(val matchId: String) : LobbyEvent
    data object KeepAlive : LobbyEvent
    data class Error(val message: String) : LobbyEvent
}