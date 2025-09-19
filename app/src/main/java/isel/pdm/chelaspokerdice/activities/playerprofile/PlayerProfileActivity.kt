package isel.pdm.chelaspokerdice.activities.playerprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import isel.pdm.chelaspokerdice.components.NavigationManager
import isel.pdm.chelaspokerdice.components.Route

class PlayerProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayerProfileScreen(navToTitle())
        }
    }
    @Composable
    private fun navToTitle(): () -> Unit = { NavigationManager.navigate(this, Route.TitleScreen) }

}