package isel.pdm.pokerdice.ui.activities.screens.pd2

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.activities.screens.pd2.composition.GameUpperBar
import isel.pdm.pokerdice.ui.activities.screens.pd2.composition.RoundBottomRow
import isel.pdm.pokerdice.ui.activities.screens.pd2.dices.RowDices
import isel.pdm.pokerdice.ui.activities.screens.pd2.states.AnimatedPlayersRow
import isel.pdm.pokerdice.ui.activities.screens.game.transition.CloseCourtain
import isel.pdm.pokerdice.ui.activities.screens.game.transition.OpenCourtain
import isel.pdm.pokerdice.ui.activities.screens.pd2.states.RoundInitBottomRow
import isel.pdm.pokerdice.ui.activities.screens.pd2.table.PokerDiceTable
import isel.pdm.pokerdice.ui.viewmodels.GameViewModel
import kotlinx.coroutines.delay


@Composable
fun PokerDiceScreen(gvm: GameViewModel) {

    when(val state = gvm.state){
        is GameViewModel.State.Error -> TODO()
        GameViewModel.State.Idle -> TODO()
        GameViewModel.State.MatchInit -> {
            var isCurtainClosed by remember { mutableStateOf(false) }
            CommonLayout(
                bottomRow = {
                    RoundInitBottomRow( { isCurtainClosed = true })
                },
            ) { AnimatedPlayersRow() }
            CloseCourtain(isCurtainClosed, gvm)
        }
        is GameViewModel.State.PlayingInRound ->{
            // Track dice faces and selection state
            var diceFaces by remember { mutableStateOf(List(5) { "9" }) }
            var selectedDice by remember { mutableStateOf<List<Boolean>>(List(5) { false }) }
            var isRolling by remember { mutableStateOf(false) }
            var elapsedTime by remember { mutableStateOf(0L) }

            // Animation duration in milliseconds
            val animationDuration = 5000L
            // Interval between face changes in milliseconds
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

            CommonLayout(
                bottomRow = {
                    RoundBottomRow(
                        enabled = !isRolling,
                        onClick = {
                            isRolling = true
                            // You might also want to trigger the actual roll in your ViewModel
                            // gvm.rollDice(selectedDice)
                        }
                    )
                },
                {
                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ){
                        RowDices(
                            faces = diceFaces,
                            selectedIndices = selectedDice.mapIndexedNotNull { index, selected ->
                                if (selected) index else null
                            },
                            onDiceSelected = { index ->
                                selectedDice = selectedDice.mapIndexed { i, selected ->
                                    if (i == index) !selected else selected
                                }
                            }
                        )
                    }
                }
            )
            OpenCourtain()
        }
    }
}

@Composable
private fun CommonLayout(
    bottomRow: @Composable () -> Unit,
    tableContent: @Composable (PaddingValues) -> Unit = {},
    screenContent: @Composable () -> Unit = {}
){
    Scaffold(
        topBar = { GameUpperBar() },
        floatingActionButton = { bottomRow() }
    ) { paddingValues ->
        GameScreenBox{
            PokerDiceTable{
                tableContent(paddingValues)
            }
            screenContent()
        }
    }
}

@Composable
fun SlotMachineDice(
    face: String,
    isRolling: Boolean,
    delayIndex: Int,
    modifier: Modifier = Modifier
) {
    val allFaces = listOf("9", "10", "J", "Q", "K", "A")
    var visibleFace by remember { mutableStateOf(face) }
    var animationProgress by remember { mutableStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "slotMachine")

    val scrollProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scrollProgress"
    )

    LaunchedEffect(isRolling, face) {
        if (isRolling) {
            // Rolagem rápida durante a animação
            animationProgress = scrollProgress
            visibleFace = allFaces.random()
        } else {
            // Parar na face final
            visibleFace = face
            animationProgress = 0f
        }
    }

    Box(
        modifier = modifier
            .size(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(2.dp, Color.Red, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Efeito de movimento vertical durante a rolagem
        Column(
            modifier = Modifier
                .graphicsLayer {
                    if (isRolling) {
                        translationY = (animationProgress * 100 - 50).dp.toPx()
                    }
                }
        ) {
            // Mostrar múltiplas faces durante a rolagem
            if (isRolling) {
                allFaces.forEach { faceItem ->
                    Text(
                        text = faceItem,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.height(70.dp)
                    )
                }
            } else {
                Text(
                    text = visibleFace,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Efeito de brilho durante a rolagem
        if (isRolling) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Yellow.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}
