package isel.pdm.pokerdice.ui.viewmodels.match

sealed class MatchNavigation {
    data class ToLobby(val lobbyId: String): MatchNavigation()
    data object ToTitle: MatchNavigation()
}