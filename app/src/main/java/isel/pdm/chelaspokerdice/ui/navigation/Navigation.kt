package isel.pdm.chelaspokerdice.ui.navigation


sealed class Navigation

sealed class TitleNavigation: Navigation(){
    data object ToPlayerProfile: TitleNavigation()
    data object ToLobbies: TitleNavigation()
    data object ToAbout: TitleNavigation()
}
sealed class PlayerProfileNavigation: Navigation(){
    data object ToTitleScreen: PlayerProfileNavigation()
    data object ToLoginScreen: PlayerProfileNavigation()
}

sealed class LobbiesNavigation: Navigation(){
    data object ToTitleScreen: LobbiesNavigation()
    data object ToCreateLobby: LobbiesNavigation()
    data object ToLobby: LobbiesNavigation()
}

sealed class CreateLobbyNavigation: Navigation(){
    data object ToLobbies: CreateLobbyNavigation()
    data object ToLobby: CreateLobbyNavigation()
}

sealed class LobbyNavigation: Navigation(){
    data object ToGame: LobbyNavigation()
    data object ToHome: LobbyNavigation()
}

sealed class AboutNavigation: Navigation(){
    data object ToTitleScreen: AboutNavigation()
    data class ToUri(val uri: String): AboutNavigation()
    data class ToMail(val emails: List<String>, val subject: String): AboutNavigation()
}
