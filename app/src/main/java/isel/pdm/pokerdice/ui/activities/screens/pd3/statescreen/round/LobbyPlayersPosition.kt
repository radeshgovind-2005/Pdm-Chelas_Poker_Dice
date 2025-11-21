package isel.pdm.pokerdice.ui.activities.screens.pd3.statescreen.round

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.Users
import isel.pdm.pokerdice.ui.activities.screens.game.elements.players.PLAYER_SIZE_DP
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.theme.DarkWhite

@Composable
fun LobbyPlayersPosition(players: Users) {
    if(players.size !in (2..6)) return
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    FirstPlayer(players[0],screenHeight,screenWidth)
    SecondPlayer(players[1],screenHeight,screenWidth)
    if(players.size == 2) return
    ThirdPlayer(players[2],screenHeight,screenWidth)
    if(players.size == 3) return
    FourthPlayer(players[3],screenHeight,screenWidth)
    if(players.size == 4) return
    FifthPlayer(players[4],screenHeight,screenWidth)
    if(players.size == 5) return
    SixthPlayer(players[5],screenHeight,screenWidth)
}
@Composable
private fun Player(name: String,modifier: Modifier){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleIcon(MyIcon.Player, tint = DarkWhite)
            Spacer(modifier = Modifier.height(3.dp))
            Text(name, color = DarkWhite)
        }
    }
}
@Composable
private fun FirstPlayer(player: User, height: Int, widht: Int){
    val m= Modifier.offset(x=(widht-PLAYER_SIZE_DP - 8).dp,y=(2*height/6).dp)
    Player(player.userCredentials.username,m)
}
@Composable
private fun SecondPlayer(player: User, height: Int, widht: Int){
    val m= Modifier.offset(x=(widht-(2*PLAYER_SIZE_DP) ).dp,y=(3*height/6).dp)
    Player(player.userCredentials.username,m)
}

@Composable
private fun ThirdPlayer(player: User, height: Int, widht: Int){
    val m= Modifier.offset(x=(widht-(3*PLAYER_SIZE_DP) - 8).dp,y=(3.55*height/6).dp)
    Player(player.userCredentials.username,m)
}
@Composable
private fun FourthPlayer(player: User, height: Int, widht: Int){
    val m= Modifier.offset(x=(widht*0.2f).dp,y=(3.55*height/6).dp)
    Player(player.userCredentials.username,m)
}

@Composable
private fun FifthPlayer(player: User, height: Int, widht: Int){
    val m= Modifier.offset(x=(widht*0.1f).dp,y=(3*height/6).dp)
    Player(player.userCredentials.username,m)
}
@Composable
private fun SixthPlayer(player: User, height: Int, widht: Int){
    val m= Modifier.offset(x=(widht*0.02f).dp,y=(2*height/6).dp)
    Player(player.userCredentials.username,m)
}

