package isel.pdm.pokerdice.ui.navigation

import isel.pdm.pokerdice.domain.Match
import isel.pdm.pokerdice.ui.activities.AboutActivity
import isel.pdm.pokerdice.ui.activities.CreateLobbyActivity
import isel.pdm.pokerdice.ui.activities.GameActivity
import isel.pdm.pokerdice.ui.activities.LobbiesActivity
import isel.pdm.pokerdice.ui.activities.LobbyActivity
import isel.pdm.pokerdice.ui.activities.MainActivity
import isel.pdm.pokerdice.ui.activities.PlayerProfileActivity
import isel.pdm.pokerdice.ui.activities.TitleActivity
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.aboutClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.authClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.createLobbyClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.gameClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.lobbiesClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.lobbyClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.playerProfileClass
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.titleClass

/**
 * Sealed class with the navigations between Activities
 * */
sealed class Navigation {

    val playerProfileClass = PlayerProfileActivity::class.java
    val createLobbyClass = CreateLobbyActivity::class.java
    val lobbiesClass = LobbiesActivity::class.java
    val lobbyClass = LobbyActivity::class.java
    val aboutClass = AboutActivity::class.java
    val authClass = MainActivity::class.java
    val titleClass = MainActivity::class.java
    val gameClass = GameActivity::class.java

    // Navigations on Title Screen
    sealed class OnTitleScreen(val dest: Class<*>): Navigation(){
        data object ToPlayerProfile: OnTitleScreen(playerProfileClass)
        data object ToLobbies: OnTitleScreen(lobbiesClass)
        data object ToAbout: OnTitleScreen(aboutClass)
    }

    // Navigations on About Screen
    sealed class OnAbout: Navigation(){
        data object GoBack: OnAbout()
        data class ToWeb(val link: String): OnAbout()
        data class ToMail(val sendTo: List<String>, val subject: String): OnAbout()
    }

    // Navigations on Lobbies Screen
    sealed class OnLobbies(val dest: Class<*>? = null): Navigation(){
        data object GoBack: OnLobbies()
        data object ToLobby: OnLobbies(lobbyClass)
        data object ToCreateLobby: OnLobbies(createLobbyClass)
    }

    // Navigations on Lobby Screen
    sealed class OnLobby(val dest: Class<*>? = null): Navigation(){
        data object GoBack: OnLobby()
        data class ToGame(val match: Match): OnLobby(gameClass)
    }

    // Navigations on Create Lobby Screen
    sealed class OnCreateLobby(val dest: Class<*>? = null): Navigation(){
        data object GoBack: OnCreateLobby()
        data object ToLobby: OnCreateLobby(lobbyClass)
    }

    // Navigations on Player Profile Screen
    sealed class OnPlayerProfile(val dest: Class<*>? = null): Navigation(){
        data object GoBack: OnPlayerProfile()
        data object Logout: OnPlayerProfile(authClass)
    }

    // Navigations on Athentication Screen
    sealed class OnAuth(val dest: Class<*>): Navigation(){
        data object ToTitle: OnAuth(titleClass)
    }

    // Navigations on Athentication Screen
    sealed class OnGame: Navigation()


}