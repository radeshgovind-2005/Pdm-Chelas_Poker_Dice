package isel.pdm.pokerdice.ui.viewmodels.login

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.types.Password
import isel.pdm.pokerdice.domain.types.Username
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.usecases.AuthUseCase

@Suppress("DEPRECATION")
class LoginViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val usecase: AuthUseCase
) : BaseViewModel<LoginState, LoginNavigation>(
    LoginState(username = savedStateHandle[KEY_USERNAME] ?: "")
){

    private val logger = AppLog(this::class.java.simpleName)
    companion object {
        private const val KEY_USERNAME = "username"

        fun provideFactory(
            owner: SavedStateRegistryOwner,
            usecase: AuthUseCase
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, null) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T = if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    LoginViewModel(handle, usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
            }
    }

    init {
        val savedUser = state.value.username
        if (savedUser.isNotEmpty()) {
            validateUsername(savedUser)
        }
    }

    fun onUsernameChange(input: String) {
        logger.v("Username input changed: $input")
        savedStateHandle[KEY_USERNAME] = input
        validateUsername(input)
    }


    private fun validateUsername(input: String) {
        val error = Username.isValid(input)
        setState {
            copy(
                username = input,
                usernameError = error,
                isLoginEnabled = error == null && passError == null &&
                        input.isNotEmpty() && pass.isNotEmpty()
            )
        }
    }

    fun onPasswordChange(input: String) {
        logger.v("Password input changed: ${input.map{'*'}.joinToString("")}")
        val error = Password.isValid(input)
        setState {
            copy(
                pass = input,
                passError = error,
                isLoginEnabled =
                    error == null && usernameError == null &&
                    username.isNotEmpty() && input.isNotEmpty()
            )
        }
    }

    fun signIn(){
        if (!state.value.isLoginEnabled) {
            logger.w("SignIn clicked but button should be disabled.")
            return
        }
        logger.i("Attempting login for user: ${state.value.username}")
        setState { copy(isLoading = true) }

        val safeUsername = Username(state.value.username)
        val safePass = Password(state.value.pass)

        launchWithHandler(
            onError = { e ->
                logger.e("Login crashed", e)
                setState { copy(isLoading = false, passError = e.message) }
            }
        ) {
            usecase
                .performLogin(safeUsername, safePass)
                .fold(
                    onSuccess = {
                        logger.i("Login successful. Navigating to Title.")
                        setState { copy(isLoading = false) }
                        sendEffect(LoginNavigation.ToTitle)
                    },
                    onFailure = { e ->
                        logger.w("Login failed: ${e.message}")
                        setState { copy(isLoading = false, passError = e.message) }
                    }
                )
        }
    }
}