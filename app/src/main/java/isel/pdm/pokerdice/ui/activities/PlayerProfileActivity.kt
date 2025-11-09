package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import isel.pdm.pokerdice.ui.activities.screens.playerprofile.PlayerProfileScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme

class PlayerProfileActivity : NavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokerDiceTheme {
                PlayerProfileScreen(
                    navBack = { navigate(Navigation.OnPlayerProfile.GoBack) },
                    logout = {  navigate(Navigation.OnPlayerProfile.Logout) },
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun navigate(nav: Navigation.OnPlayerProfile) =
        when (nav) {
            Navigation.OnPlayerProfile.GoBack -> finish()
            Navigation.OnPlayerProfile.Logout -> toClearScreen(MainActivity::class.java,Anim.Backwards)
        }
}