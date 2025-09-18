package isel.pdm.chelaspokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import isel.pdm.chelaspokerdice.activities.title.TitleScreen

class MainActivity : ComponentActivity() {

    private val tag = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TitleScreen(navToProfile(), navToLobbies(), navToAbout())
        }
    }

    @Composable
    private fun navToProfile(): () -> Unit = { NavigationManager.navigate(this, Route.PlayerProfile) }
    @Composable
    private fun navToLobbies(): () -> Unit = { NavigationManager.navigate(this, Route.Lobbies) }
    @Composable
    private fun navToAbout(): () -> Unit = { NavigationManager.navigate(this, Route.About) }

}

