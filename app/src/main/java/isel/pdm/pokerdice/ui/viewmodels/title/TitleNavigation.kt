package isel.pdm.pokerdice.ui.viewmodels.title

sealed class TitleNavigation {
    data object ToLobbies : TitleNavigation()
    data object ToProfile : TitleNavigation()
    data object ToAbout : TitleNavigation()
}