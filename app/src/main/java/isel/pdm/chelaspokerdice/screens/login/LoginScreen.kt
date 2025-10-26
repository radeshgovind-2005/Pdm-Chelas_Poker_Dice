package isel.pdm.chelaspokerdice.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.login.struct.LoginScaffold
import isel.pdm.chelaspokerdice.ui.components.elements.ButtonText
import isel.pdm.chelaspokerdice.ui.components.elements.TitleText
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard

class LoginScreen(
    private val onNavigateToGame: () -> Unit = {},
    private val onNavigateToHome: () -> Unit = {}
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LoginScreen {}
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LoginScreen {}
    }

    @SuppressLint("NotConstructor")
    @Composable
    private fun LoginScreen(content: @Composable () -> Unit) {
        LoginScaffold(Modifier) { innerPadding ->
            ScrollableContentColumnDisplay(Modifier, innerPadding, Arrangement.Center) {
                content()
                SimpleCard(Modifier) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TitleText("Welcome to\n")
                        TitleText("CHELAS")
                        TitleText("POKER DICE")
                    }
                    Spacer(Modifier.height(50.dp))
                    TextField(
                        value = "", // You'll want to use remember { mutableStateOf("") } for actual implementation
                        onValueChange = { /* Handle lobby name change */ },
                        label = { Text("Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true
                    )
                    TextField(
                        value = "", // You'll want to use remember { mutableStateOf("") } for actual implementation
                        onValueChange = { /* Handle lobby name change */ },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true
                    )
                    Spacer(Modifier.height(25.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        ButtonText("Login") { onNavigateToHome() }
                    }

            }
        }
    }
}}


@PreviewScreenSizes
@Composable
private fun Preview() {
    LoginScreen().Render(Modifier)
}