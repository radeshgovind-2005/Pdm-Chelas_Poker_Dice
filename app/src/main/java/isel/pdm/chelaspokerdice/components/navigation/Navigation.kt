package isel.pdm.chelaspokerdice.components.navigation

import android.content.Context
import android.content.Intent
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.activities.about.AboutActivity
import isel.pdm.chelaspokerdice.activities.lobbies.LobbiesActivity
import isel.pdm.chelaspokerdice.activities.playerprofile.PlayerProfileActivity

enum class Route { TitleScreen, About, PlayerProfile, Lobbies }


sealed class NavigationScreen(
    val route: Route,
    val destination: Class<*>
) {

    object Title : NavigationScreen(Route.TitleScreen, MainActivity::class.java)
    object About : NavigationScreen(Route.About, AboutActivity::class.java)
    object PlayerProfile : NavigationScreen(Route.PlayerProfile, PlayerProfileActivity::class.java)
    object Lobbies : NavigationScreen(Route.Lobbies, LobbiesActivity::class.java)



    fun createIntent(context: Context): Intent = Intent(context, destination)
}

object NavigationManager {

    fun getScreen(route: Route): NavigationScreen =
        when (route) {
            Route.TitleScreen -> NavigationScreen.Title
            Route.Lobbies -> NavigationScreen.Lobbies
            Route.PlayerProfile -> NavigationScreen.PlayerProfile
            Route.About -> NavigationScreen.About
        }

    fun navigate(context: Context, route: Route) {
        getScreen(route).let { screen ->
            val intent = screen.createIntent(context)
            context.startActivity(intent)
        }
    }

}
