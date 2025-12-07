package isel.pdm.pokerdice.ui.viewmodels.login

data class LoginState(
    //input box text:
    val username: String = "",
    val pass: String = "",
    //input box errors
    val usernameError: String? = null,
    val passError: String? = null,
    //button is enabled
    val isLoginEnabled: Boolean = false,
    //is logging in
    val isLoading: Boolean = false,
)