package isel.pdm.pokerdice.ui.navigation

/**
 * Sealed class with the navigations between Activities
 * */
sealed class Navigation {

    // Navigations on Title Screen
    sealed class OnTitleScreen: Navigation(){
        data object ToPlayerProfile: OnTitleScreen()
        data object ToLobbies: OnTitleScreen()
        data object ToAbout: OnTitleScreen()
    }

    // Navigations on About Screen
    sealed class OnAbout: Navigation(){
        data object GoBack: OnAbout()
        data class ToWeb(val link: String): OnAbout()
        data class ToMail(val sendTo: List<String>, val subject: String): OnAbout()
    }

    // Navigations on Lobbies Screen
    sealed class OnLobbies: Navigation(){
        data object GoBack: OnLobbies()
        data object ToLobby: OnLobbies()
        data object ToCreateLobby: OnLobbies()
    }

    // Navigations on Lobby Screen
    sealed class OnLobby: Navigation(){
        data object GoBack: OnLobby()
        data object ToGame: OnLobby()
    }

    // Navigations on Create Lobby Screen
    sealed class OnCreateLobby: Navigation(){
        data object GoBack: OnCreateLobby()
        data object ToLobby: OnCreateLobby()
    }

    // Navigations on Player Profile Screen
    sealed class OnPlayerProfile: Navigation(){
        data object GoBack: OnPlayerProfile()
        data object Logout: OnPlayerProfile()
    }

    // Navigations on Athentication Screen
    sealed class OnAuth: Navigation(){
        data object ToTitle: OnAuth()
    }
}