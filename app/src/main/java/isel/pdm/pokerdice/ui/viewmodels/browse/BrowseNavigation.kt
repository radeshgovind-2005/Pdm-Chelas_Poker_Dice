package isel.pdm.pokerdice.ui.viewmodels.browse

sealed class BrowseNavigation {
    data object ToTitle : BrowseNavigation()
    data class ToLobby(val lobbyId: String) : BrowseNavigation()
    data object ToCreateLobby : BrowseNavigation()
}