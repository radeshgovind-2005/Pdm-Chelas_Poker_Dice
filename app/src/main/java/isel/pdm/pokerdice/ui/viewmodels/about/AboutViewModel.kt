package isel.pdm.pokerdice.ui.viewmodels.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.usecases.AboutUseCase

class AboutViewModel(
    private val usecase: AboutUseCase
) : BaseViewModel<AboutState, AboutNavigation>(AboutState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: AboutUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
                    AboutViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun onBackRequest() {
        sendEffect(AboutNavigation.ToTitle)
    }

    fun onMailRequest(sendTo: List<String>, subject: String) {
        sendEffect(AboutNavigation.ToMail(sendTo,subject))
    }

    fun onWebRequest(uri: String) {
        sendEffect(AboutNavigation.ToWeb(uri))
    }

}