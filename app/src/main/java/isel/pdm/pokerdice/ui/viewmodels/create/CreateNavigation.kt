package isel.pdm.pokerdice.ui.viewmodels.create

sealed class CreateNavigation {
    data class ToLobby(val lobbyId: String): CreateNavigation()

    data object ToBrowse: CreateNavigation()
}