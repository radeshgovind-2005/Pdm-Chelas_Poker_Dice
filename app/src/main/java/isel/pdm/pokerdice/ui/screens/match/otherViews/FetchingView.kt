package isel.pdm.pokerdice.ui.screens.match.otherViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.common.components.card.SimpleCard
import isel.pdm.pokerdice.ui.common.components.text.BoldTitle
import isel.pdm.pokerdice.ui.common.layouts.background.DarkRedBackground

@Composable
fun FetchingView() {
    DarkRedBackground {
        SimpleCard {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                BoldTitle(stringResource(R.string.match_fetching))
                Spacer(Modifier.height(32.dp))
                LinearProgressIndicator()
            }
        }
    }
}