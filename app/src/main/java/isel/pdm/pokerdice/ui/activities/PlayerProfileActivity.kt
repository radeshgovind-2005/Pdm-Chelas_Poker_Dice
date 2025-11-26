package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import isel.pdm.pokerdice.PlayerProfileLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.playerprofile.PlayerProfileScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme

class PlayerProfileActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerProfileLog.logLifeCycle(getCurrentMethodName())
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
        PlayerProfileLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onDestroy() {
        super.onDestroy()
        PlayerProfileLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onResume() {
        super.onResume()
        PlayerProfileLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnPlayerProfile) {
        PlayerProfileLog.logNavigation(nav)
        when (nav) {
            Navigation.OnPlayerProfile.GoBack -> finish()
            Navigation.OnPlayerProfile.Logout -> toClearScreen(
                MainActivity::class.java,
                Anim.Backwards
            )
        }
    }
}