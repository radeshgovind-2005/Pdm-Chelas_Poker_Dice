package isel.pdm.pokerdice.ui.activities.screens.game.elements.players

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.theme.DarkWhite

const val PLAYER_SIZE_DP = 60

@Composable
fun PlayerFigure(name: String,modifier: Modifier){
    Box(
        modifier = modifier.size(PLAYER_SIZE_DP.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleIcon(MyIcon.Player, tint = DarkWhite)
            Spacer(modifier = Modifier.height(6.dp))
            Text(name, color = DarkWhite)
        }
    }
}