package isel.pdm.pokerdice

import android.util.Log
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel

private val APP_BASE_TAG = "PokerDiceGame"

/**
 * A type-safe, centralized data structure for all logging tags in the app.
 *
 * It constructs a namespaced tag (e.g., "PokerDiceGame") that allows for
 * easy, hierarchical filtering in Logcat.
 *
 * @param category The specific category for this log message.
 */
sealed class LogTag(private val category: String){
    val tag: String = "$APP_BASE_TAG-$category"
    fun logDebug(message: String){ Log.d(tag, message) }
    fun logLifeCycle(state: String){
        when(state) {
            "onCreate"-> Log.v(tag, "-------------New $category Activity-----------")
        }
        Log.v(tag, "$category Activity state: $state")
    }
    fun logNavigation(nav: Navigation){ Log.i(tag, "$category Activity navigation to: $nav")}
    fun logVm(vm: BaseViewModel<*>, method: String, msg:String? = null){
        val phrase ="${vm.javaClass.simpleName} state is ${vm.state::class.java.simpleName} at $method method."
        msg?.let{
            Log.w(tag,phrase + " Message: $it")
        } ?: Log.d(tag,phrase)
    }
}

data object AuthLog: LogTag("Auth")
data object Lobby: LogTag("Lobby")
data object GameLog: LogTag("Game")
data object TitleLog: LogTag("Title")
data object LobbiesLog: LogTag("Lobbies")
data object LobbyLog: LogTag("Lobby")
data object AboutLog: LogTag("About")
data object PlayerProfileLog: LogTag("PlayerProfile")
data object CreateLobbyLog: LogTag("CreateLobby")

fun getCurrentMethodName(): String {
    return Throwable().stackTrace[1].methodName
}
