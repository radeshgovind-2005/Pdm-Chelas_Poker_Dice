package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import isel.pdm.pokerdice.ui.activities.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme

class TitleActivity : NavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokerDiceTheme {
                TitleScreen(
                    navToPlayerProfile = {navigate((Navigation.OnTitleScreen.ToPlayerProfile))},
                    navToAbout = { navigate(Navigation.OnTitleScreen.ToAbout)},
                    navToLobbies = {navigate(Navigation.OnTitleScreen.ToLobbies)}
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun navigate(nav: Navigation.OnTitleScreen) =
        when(nav){
            Navigation.OnTitleScreen.ToLobbies -> toScreen(LobbiesActivity::class.java,Anim.Forward)
            Navigation.OnTitleScreen.ToAbout -> toScreen(AboutActivity::class.java,Anim.Forward)
            Navigation.OnTitleScreen.ToPlayerProfile ->toScreen(PlayerProfileActivity::class.java,Anim.Forward)
        }
}