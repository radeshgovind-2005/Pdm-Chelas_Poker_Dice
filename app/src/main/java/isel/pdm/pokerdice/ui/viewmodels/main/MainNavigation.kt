package isel.pdm.pokerdice.ui.viewmodels.main

import isel.pdm.pokerdice.ui.viewmodels.title.TitleNavigation


sealed class MainNavigation {
    data class ToLobby(val lobbyId: String) : MainNavigation()
    data class ToMatch(val matchId: String) : MainNavigation()
    data object ToLogin : MainNavigation()
    data object ToTitle : MainNavigation()
}