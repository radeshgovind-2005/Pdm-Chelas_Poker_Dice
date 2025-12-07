package isel.pdm.pokerdice.ui.viewmodels.profile

sealed class ProfileNavigation {
    data object ToTitle : ProfileNavigation()
    data object ToLogin : ProfileNavigation()
}