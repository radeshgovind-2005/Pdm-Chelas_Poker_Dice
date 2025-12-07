package isel.pdm.pokerdice.ui.viewmodels.login

sealed class LoginNavigation {
    data object ToTitle : LoginNavigation()
}