package isel.pdm.pokerdice.ui.activities.screens.game.elements.players

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.Players
import isel.pdm.pokerdice.domain.Player
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.theme.DarkWhite

@Composable
fun PokerPlayers(players: Players, turn: Player?) {
    if(players.size !in (MIN_PLAYERS..MAX_PLAYERS)) return
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    players.forEachIndexed { idx,p ->
        val name = p.user.userCredentials.username
        val position = playersPosition[idx]
        val playerModifier = Modifier.offset(
            x=position.xOffset(screenWidth),
            y=position.yOffset(screenHeight)
        )
        val spaceModifier = Modifier.height(3.dp)
        val tint = if(p==turn)Color.Yellow else DarkWhite
        Box(playerModifier, Alignment.Center){
            Column(horizontalAlignment=Alignment.CenterHorizontally){
                SimpleIcon(MyIcon.Player,tint=tint)
                Spacer(spaceModifier)
                Text(name, color = DarkWhite)
            }
        }
    }
}