package isel.pdm.pokerdice.ui.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.domain.usecases.ProfileUseCase

class ProfileViewModel(
    private val usecase: ProfileUseCase
) : BaseViewModel<ProfileState, ProfileNavigation>(ProfileState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: ProfileUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                    ProfileViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun onCreateActivity(){
        launchWithHandler(
            onError = { e->
                logger.e("Error fetching stats", e)
                setState { copy(isLoading = false) }
            }
        ) {
            usecase
                .getStats()
                .fold(
                    onSuccess = { res ->
                        setState {
                            copy(
                                isLoading=false,
                                stats = res.first,
                                username = res.second
                            )
                        }
                    },
                    onFailure = { e->
                        logger.e("Error: ${e.message}", e)
                        setState { copy(isLoading = false) }
                    }
                )
        }

    }
    fun onBackRequest() {
        sendEffect(ProfileNavigation.ToTitle)
    }

    fun onLogoutRequest() {
        setState { copy(showLogoutDialog = true) }
    }

    fun onLogoutCancel() {
        setState { copy(showLogoutDialog = false) }
    }

    fun onLogoutConfirm() {
        setState { copy(showLogoutDialog = false, isLoading = true) }
        launchWithHandler(
            onError = { e ->
                logger.e("Logout failed", e)
                setState { copy(isLoading = false) }
            }
        ) {
            logger.i("Performing Logout")
            usecase.logout()
            sendEffect(ProfileNavigation.ToLogin)
        }
    }

}