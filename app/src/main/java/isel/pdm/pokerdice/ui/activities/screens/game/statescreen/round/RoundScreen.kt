package isel.pdm.pokerdice.ui.activities.screens.game.statescreen.round

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.ui.activities.screens.game.GameLayout
import isel.pdm.pokerdice.ui.activities.screens.game.transition.OpenCourtain
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import kotlinx.coroutines.delay

@Composable
fun RoundScreen(lobby: Lobby) {
    var isRolling by remember { mutableStateOf(false) }

    var diceFaces by remember { mutableStateOf(List(5) { "9" }) }
    var selectedDice by remember { mutableStateOf<List<Boolean>>(List(5) { false }) }
    var elapsedTime by remember { mutableStateOf(0L) }
    val animationDuration = 5000L
    val changeInterval = 100L

    LaunchedEffect(isRolling) {
        if (isRolling) {
            elapsedTime = 0L
            val startTime = System.currentTimeMillis()

            while (elapsedTime < animationDuration) {
                // Update only non-selected dice or all if none are selected
                val newFaces = diceFaces.mapIndexed { index, currentFace ->
                    if (!selectedDice[index] || selectedDice.none { it }) {
                        // Reroll dice that are not selected, or all if none are selected
                        listOf("9", "10", "J", "Q", "K", "A").random()
                    } else {
                        // Keep selected dice the same
                        currentFace
                    }
                }
                diceFaces = newFaces
                delay(changeInterval)
                elapsedTime = System.currentTimeMillis() - startTime
            }

            // Final roll with actual game logic
            // diceFaces = gvm.getCurrentDiceFaces() // Use actual game state
            isRolling = false

            // Clear selection after roll (optional - depends on your game rules)
            // selectedDice = List(5) { false }
        }
    }

    GameLayout(
        bottomRow={BottomRow(!isRolling){isRolling = true} },
        upperRow = {UpperRow()},
        tableContent = {
            FiveDices(
                faces=diceFaces,
                selectedIndices=selectedDice.mapIndexedNotNull { index, selected ->
                    if (selected) index else null
                },
                onSelect = { index ->
                    selectedDice = selectedDice.mapIndexed { i, selected ->
                        if (i == index) !selected else selected
                    }
                },
                isTurn = false
            )
        },
        screenContent = {
            LobbyPlayersPosition(lobby.lobbyPlayers)
        }
    )
    OpenCourtain()
}

@Composable
private fun BottomRow(enabled: Boolean=true,onClick: () -> Unit = {}){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        ButtonText(
            text=stringResource(R.string.roll_all_btn),
            modifier = Modifier.size(300.dp,50.dp),
            onClick = {onClick()},
            color = ButtonDefaults.buttonColors(
                containerColor = MediumDarkRed,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            enabled = enabled
        )
    }
}
@Composable
private fun UpperRow(){
    var timerValue by remember { mutableIntStateOf(15) }
    LaunchedEffect(key1 = timerValue) {
        if (timerValue > 0) {
            delay(1000L) // Delay de 1 segundo
            timerValue--
        } else {
            // Quando chega a zero, reinicia para 15
            timerValue = 15
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(Modifier.height(16.dp).width(30.dp))
        Text(
            text = timerValue.toString(),
            color = if(timerValue >5)Color.White else Color.Red,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}