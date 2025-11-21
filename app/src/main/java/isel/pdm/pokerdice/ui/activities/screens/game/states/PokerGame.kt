package isel.pdm.pokerdice.ui.activities.screens.game.states

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.ui.activities.screens.game.GameMainLayout
import isel.pdm.pokerdice.ui.activities.screens.game.effects.OpenCurtain
import isel.pdm.pokerdice.ui.activities.screens.game.elements.InfoBox
import isel.pdm.pokerdice.ui.activities.screens.game.elements.players.PokerPlayers
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import isel.pdm.pokerdice.ui.theme.PureBlack

private val SHADOW_COLOR = Color(0x40000000)

@Composable
fun PokerGame(lobby: Lobby, user: User) {
    var infoBoxOpen by remember { mutableStateOf(false) }
    GameMainLayout(
        upperRow = {
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(horizontal=10.dp, vertical=25.dp)
            ){
                Box{
                    Surface(
                        shape = CircleShape,
                        color = DefaultTintColor
                    ) {
                        IconButton(
                            onClick = { infoBoxOpen = !infoBoxOpen },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Information",
                                tint = PureBlack,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                    }
                }
            }
        },
        bottomRow = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp*0.2).dp),
                horizontalArrangement = Arrangement.Center,
            ){
                ButtonText(
                    text=stringResource(R.string.roll_all_btn),
                    modifier = Modifier.size(300.dp,50.dp),
                    onClick = {},
                    color = ButtonDefaults.buttonColors(
                        containerColor = MediumDarkRed,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    enabled = lobby.turn==user
                )
            }
        },
        tableContent = {
            var dices by remember { mutableStateOf(List(5) { "9" }) }
            var isSelected by remember { mutableStateOf(false) }
            /**
             * GVM:
             *  -turn is gold
             *  -dices values? if null is rolling
             *  -
             * */
            OneFullColumn {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dices.forEach {

                        val diceSize = if (isSelected) 48.dp else 40.dp
                        val borderWidth = if (isSelected) 3.dp else 0.dp
                        val borderColor = if (isSelected) Color.Black else Color.Transparent
                        val diceColor = if (lobby.turn==user) Color.White else Color.LightGray
                        val faceColor =
                            if (lobby.turn==user) listOf(Color.Black, Color.Red).random() else Color.DarkGray
                        Box(
                            modifier = Modifier
                                .size(diceSize)
                                .background(
                                    color = diceColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = borderWidth,
                                    color = borderColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { }
                                .drawWithCache {
                                    onDrawBehind {
                                        drawRect(
                                            color = SHADOW_COLOR,
                                            topLeft = Offset(2f, 2f),
                                            size = size.copy(
                                                width = size.width - 4f,
                                                height = size.height - 4f
                                            )
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(it ?: "?", color = faceColor)
                        }
                    }
                }
            }
        },
        screenContent = {
            PokerPlayers(lobby.lobbyPlayers,lobby.turn)
            if(infoBoxOpen) InfoBox()
        }
    )
    OpenCurtain()
}