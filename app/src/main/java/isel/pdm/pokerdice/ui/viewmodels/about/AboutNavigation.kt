package isel.pdm.pokerdice.ui.viewmodels.about

sealed class AboutNavigation {
    data object ToTitle : AboutNavigation()
    data class ToMail(val sendTo: List<String>, val subject: String) : AboutNavigation()
    data class ToWeb(val uri: String) : AboutNavigation()
}