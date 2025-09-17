package isel.pdm.chelaspokerdice


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun TitleScreen(
    navToProfile: () -> Unit,
    navToLobbies: () -> Unit,
    navToAbout: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Yellow),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {navToProfile()},
                ) {
                    Text("Profile")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {navToLobbies()}
                ) {
                    Text("Lobbies")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {navToAbout()}
                ) {
                    Text("About")
                }
            }

        }
    }

}