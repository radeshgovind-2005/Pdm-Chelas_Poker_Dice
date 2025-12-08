package isel.pdm.pokerdice.ui.viewmodels.lobby

sealed class LobbyNavigation {
    data object ToBrowse: LobbyNavigation()
    data class ToMatch(val matchId: String): LobbyNavigation()
}