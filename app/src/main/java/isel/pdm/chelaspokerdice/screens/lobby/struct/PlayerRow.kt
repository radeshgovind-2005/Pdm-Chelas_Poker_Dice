package isel.pdm.chelaspokerdice.screens.lobby.struct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.ui.components.elements.MediumText
import isel.pdm.chelaspokerdice.ui.components.figures.icons.PlayerIcon


@Composable
fun PlayerRow(player: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerIcon(player)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            MediumText(player)
        }
        MediumText("Ready")
    }
}