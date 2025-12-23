package isel.pdm.pokerdice.ui.screens.title

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.common.components.images.PokerLogo

@Composable
fun PortraitTitleScreen(onClickLobbies: () -> Unit, btnResId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokerLogo()
        Spacer(Modifier.height(128.dp))
        Button(
            onClick = onClickLobbies,
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(stringResource(btnResId))
        }
    }
}

@Composable
fun LandscapeTitleScreen(onClickLobbies: () -> Unit, btnResId: Int) {
    Row(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokerLogo(0.dp, 2)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onClickLobbies,
                modifier = Modifier.fillMaxWidth(0.65f)
            ) {
                Text(stringResource(btnResId))
            }
        }
    }
}