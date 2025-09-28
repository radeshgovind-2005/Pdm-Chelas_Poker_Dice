package isel.pdm.chelaspokerdice.navigation


sealed class Navigation

sealed class TitleNavigation: Navigation(){
    object ToPlayerProfile: TitleNavigation()
    object ToLobbies: TitleNavigation()
    object ToAbout: TitleNavigation()
}
sealed class PlayerProfileNavigation: Navigation(){
    object ToTitleScreen: PlayerProfileNavigation()
}

sealed class LobbiesNavigation: Navigation(){
    object ToTitleScreen: LobbiesNavigation()
}

sealed class AboutNavigation: Navigation(){
    object ToTitleScreen: AboutNavigation()
    data class ToUri(val uri: String): AboutNavigation()
}
