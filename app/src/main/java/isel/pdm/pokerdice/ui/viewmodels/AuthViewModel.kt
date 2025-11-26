package isel.pdm.pokerdice.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.usecases.AuthUseCase

class AuthViewModel(
    private val authUseCase: AuthUseCase
) : BaseViewModel<AuthViewModel.State>() {

    companion object {
        fun getFactory(authUseCase: AuthUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                    AuthViewModel(authUseCase = authUseCase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override val initialState: State = State.Idle

    fun getCurrentUser(): User? {
        launch(onError = { State.Error(it) }) {
            val user = authUseCase.getLoggedUser() ?: return@launch
            val logged = State.LoggedIn(user)
            updateState(newState=logged)
        }
        return if (state.value is State.LoggedIn) (state.value as State.LoggedIn).user else null
    }

    fun login(username: String, password: String) {
        if (state.value is State.LoggingIn || state.value is State.LoggedIn) return
        launch(onError = { State.Error(it) }) {
            val credentials = UserCredentials(username,password)
            val loggingIn = State.LoggingIn(credentials)
            updateState(newState=loggingIn)

            val user = authUseCase.login(credentials)
            val logged = State.LoggedIn(user)
            updateState(newState=logged)
            Log.d("Auth","$state")
        }
    }

    sealed interface State {
        data object Idle : State
        data class Error(val e: Throwable) : State
        data class LoggingIn(val credentials: UserCredentials): State
        data class LoggingOut(val authToken: String): State
        data class LoggedIn(val user: User): State
    }
}