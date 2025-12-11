package isel.pdm.pokerdice.ui.viewmodels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.title.TitleNavigation
import isel.pdm.pokerdice.ui.viewmodels.usecases.MainUseCase

class MainViewModel (
    private val usecase: MainUseCase
) : BaseViewModel<MainState, MainNavigation>(MainState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: MainUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    MainViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun sessionCheck() {
        setState { copy(isLoading = true) }
        launchWithHandler(
            onError = { e ->
                logger.e("Session Check Crashed", e)
                setState { copy(isLoading = false, e = e.message) }
            }
        ) {
            usecase
                .sessionCheck()
                .fold(
                    onSuccess = { res ->
                        logger.i("Session existent -> $res")
                        when{
                            res.second.lobbyId == null -> sendEffect(MainNavigation.ToTitle)
                            res.second.lobbyId != null && res.second.matchId != null  -> sendEffect(MainNavigation.ToMatch(res.second.matchId!!))
                            res.second.lobbyId != null -> sendEffect(MainNavigation.ToLobby(res.second.lobbyId!!))
                        }
                        setState { copy(isLoading = false) }
                    },
                    onFailure = {
                        logger.i("Session inexistent -> Navigate to Login requested")
                        sendEffect(MainNavigation.ToLogin)
                        setState { copy(isLoading = false)
                        }
                    }
                )
        }
    }
}
