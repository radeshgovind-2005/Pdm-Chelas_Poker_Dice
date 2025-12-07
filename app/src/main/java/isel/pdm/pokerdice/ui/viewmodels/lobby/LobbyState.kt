package isel.pdm.pokerdice.ui.viewmodels.lobby

import isel.pdm.pokerdice.domain.lobby.Lobby

data class LobbyState (
    val username: String = "",
    val isLoading: Boolean= false,
    val isJoined: Boolean= false,
    val lobbyId: String = "",
    val lobby: Lobby? = null,
    val error: String? = null
)