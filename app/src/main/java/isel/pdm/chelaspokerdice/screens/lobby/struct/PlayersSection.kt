package isel.pdm.chelaspokerdice.screens.lobby.struct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.ui.components.elements.TitleMedium
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard

@Composable
fun PlayersSection(players: List<String>?) {
    if(players == null) return
    SimpleCard( Modifier.fillMaxWidth().padding(horizontal = 16.dp),) {
        Column(modifier = Modifier.padding(16.dp)) {
            TitleMedium("Players")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                players.forEach { PlayerRow(it) }
            }
        }
    }
}