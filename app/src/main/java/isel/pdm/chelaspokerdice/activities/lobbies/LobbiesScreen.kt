package isel.pdm.chelaspokerdice.activities.lobbies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.components.MainButton

@Preview(showSystemUi = true)
@Composable
fun LobbiesScreen(
    navToProfile: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            MainButton(navToProfile, stringResource(R.string.title_screen))
        }
    }
}