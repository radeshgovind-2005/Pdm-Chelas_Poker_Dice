package isel.pdm.pokerdice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.usecases.AuthUseCase
/**
 * AUTH ViewModel is the midle man between the frontend and the
 * backend regarding authentication
 * -This view model has two main states:
     * @see Authenticated and
     * @see Not_Authenticated(Idle)
     * Ohter states are consequences from operations as it is the
     * @see Loading(means that it is on a process of loggin) and
     * @see Error (menas that a operation had has a error)
 * */

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

    val bootstrappedUser: User?
        get() = (state.value as? State.Authenticated)?.user

    fun restoreUserSession(): User? {
        launch(onError = { State.Error(it) }) {
            val user = authUseCase.getLoggedUser() ?: return@launch
            updateState(State.Authenticated(user))
        }
        return bootstrappedUser
    }

    fun signIn(username: String, password: String) {
        if (state.value is State.Loading || state.value is State.Authenticated) return
        launch(onError = { State.Error(it) }) {
            updateState(State.Loading)
            val credentials = UserCredentials(username, password)
            val user = authUseCase.signIn(credentials)
            updateState(State.Authenticated(user))
        }
    }

    fun signOut() {
        if (state.value !is State.Authenticated) return
        launch(onError = { State.Error(it) }) {
            val user = (state.value as State.Authenticated).user
            authUseCase.signOut(user.userCredentials.username)
            updateState(State.Idle)
        }
    }

    sealed interface State {
        data object Idle : State
        data object Loading: State
        data class Authenticated(val user: User): State
        data class Error(val e: Throwable) : State

    }
}

/*
    fun getCurrentUser(): User? {
        launch(onError = { State.Error(it) }) {
            val user = authUseCase.getLoggedUser() ?: return@launch
            val logged = State.Authenticated(user)
            updateState(newState=logged)
        }
        return if (state.value is State.Authenticated) (state.value as State.Authenticated).user else null
    }

    fun login(username: String, password: String) {
        if (state.value is State.Loading || state.value is State.Authenticated) return
        launch(onError = { State.Error(it) }) {
            val credentials = UserCredentials(username,password)
            val loggingIn = State.Loading(credentials)
            updateState(newState=loggingIn)

            val user = authUseCase.login(credentials)
            val logged = State.Authenticated(user)
            updateState(newState=logged)
            Log.d("Auth","$state")
        }
    }

    fun logOut(){
        if(state.value !is State.Authenticated) {
            AuthLog.logVm(this, getCurrentMethodName(),"To log out avm state must be logged in")
            return
        }
        launch(onError={State.Error(it)}){
            val user  = (state.value as State.Authenticated).user
            updateState(State.LoggingOut)
            authUseCase.logOut(user.userCredentials.username)
            updateState(State.Idle)
        }
    }
 */