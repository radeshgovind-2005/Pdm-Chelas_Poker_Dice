package isel.pdm.pokerdice.ui.viewmodels.title

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.usecases.TitleUseCase


class TitleViewModel (
    private val usecase: TitleUseCase
) : BaseViewModel<TitleState, TitleNavigation>(TitleState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: TitleUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(TitleViewModel::class.java)) {
                    TitleViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun onLobbiesClicked() {
        logger.i("Navigate to Lobbies requested")
        sendEffect(TitleNavigation.ToLobbies)
    }

    fun onProfileClicked() {
        logger.i("Navigate to Profile requested")
        sendEffect(TitleNavigation.ToProfile)
    }

    fun onAboutClicked() {
        logger.i("Navigate to About requested")
        sendEffect(TitleNavigation.ToAbout)
    }
}