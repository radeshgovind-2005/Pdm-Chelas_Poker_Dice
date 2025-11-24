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
import androidx.compose.runtime.LaunchedEffect
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
import isel.pdm.pokerdice.GameLog
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.PokerHand
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.MATCH_MAX_TRIES
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.ui.activities.screens.game.GameMainLayout
import isel.pdm.pokerdice.ui.activities.screens.game.effects.OpenCurtain
import isel.pdm.pokerdice.ui.activities.screens.game.elements.InfoBox
import isel.pdm.pokerdice.ui.activities.screens.game.elements.players.PokerPlayers
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.text.PlainText
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import isel.pdm.pokerdice.ui.theme.PureBlack
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import kotlinx.coroutines.delay

private val SHADOW_COLOR = Color(0x40000000)

@Composable
fun PokerGame(mvm: MatchViewModel, lobby: Lobby, user: User) {
    val s = mvm.state as? MatchViewModel.State.MatchRunning ?: return

    var match by remember{ mutableStateOf(s.match)}
    var infoBoxOpen by remember { mutableStateOf(false) }

    var currentHand by remember { mutableStateOf(PokerHand()) }

    var elapsedTime by remember { mutableStateOf(0L) }
    var isRollingUI by remember { mutableStateOf(false) }
    LaunchedEffect(match.tries) {
        if(s.isRolling){
            elapsedTime = 0L
            val startTime = System.currentTimeMillis()
            GameLog.logDebug("NewHand Rolling, tries: ${match.tries}")
            while (elapsedTime < 3000L) {
                currentHand = currentHand.roll()
                delay(10L)
                elapsedTime = System.currentTimeMillis() - startTime
            }
            s.isRolling = false
            isRollingUI = false
        }
    }
    GameMainLayout(
        upperRow = {
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 25.dp)
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
                    .height((LocalConfiguration.current.screenHeightDp * 0.2).dp),
                horizontalArrangement = Arrangement.Center,
            ){
                ButtonText(
                    text=stringResource(R.string.roll_all_btn),
                    modifier = Modifier.size(300.dp,50.dp),
                    onClick = {
                        val newMatchState = match.copy(tries = match.tries + 1)

                        // Atualiza o ViewModel
                        s.match = newMatchState
                        s.isRolling = true

                        // Atualiza o Estado Local (isto dispara o LaunchedEffect acima)
                        match = newMatchState
                    },
                    color = ButtonDefaults.buttonColors(
                        containerColor = MediumDarkRed,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    enabled = match.turn?.user==user && match.tries < MATCH_MAX_TRIES && !isRollingUI
                )
            }
        },
        tableContent = {

            var isSelected by remember { mutableStateOf(false) }
            OneFullColumn {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    currentHand.dices.forEachIndexed { idx,dice->
                        val diceSize = if (isSelected) 48.dp else 40.dp
                        val borderWidth = if (isSelected) 3.dp else 0.dp
                        val borderColor = if (isSelected) Color.Black else Color.Transparent
                        val diceColor = if (match.turn?.user==user) Color.White else Color.LightGray
                        val faceColor =
                            if (match.turn?.user==user) listOf(Color.Black, Color.Red).random() else Color.DarkGray
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
                            Text(dice.face.symb.toString(), color = faceColor)
                        }
                    }
                }
                Row(Modifier
                    .fillMaxWidth()
                    .padding(25.dp), horizontalArrangement = Arrangement.Center){
                    PlainText("ReRolls: X${match.tries}")
                }
            }
        },
        screenContent = {
            PokerPlayers(match.players,match.turn)
            if(infoBoxOpen) InfoBox(lobby,match)
        }
    )
    OpenCurtain()
}