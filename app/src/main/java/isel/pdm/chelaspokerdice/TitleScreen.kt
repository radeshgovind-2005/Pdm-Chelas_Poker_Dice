package isel.pdm.chelaspokerdice


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import isel.pdm.chelaspokerdice.components.MainButton

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
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                MainButton(navToProfile, stringResource(R.string.profile))
                MainButton(navToLobbies, stringResource(R.string.lobbies))
                MainButton(navToAbout, stringResource(R.string.about))
            }
        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun titleScreenPreview() {
    TitleScreen(
        { }, { }, { }
    )
}
