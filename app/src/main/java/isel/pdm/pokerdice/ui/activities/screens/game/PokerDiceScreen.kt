package isel.pdm.pokerdice.ui.activities.screens.game

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.activities.screens.game.composition.GameUpperBar
import isel.pdm.pokerdice.ui.activities.screens.game.states.AnimatedCloseCurtain
import isel.pdm.pokerdice.ui.activities.screens.game.states.AnimatedCloseCurtain2
import isel.pdm.pokerdice.ui.activities.screens.game.states.AnimatedPlayersRow
import isel.pdm.pokerdice.ui.activities.screens.game.states.RoundInitBottomRow
import isel.pdm.pokerdice.ui.activities.screens.game.table.PokerDiceTable
import isel.pdm.pokerdice.ui.viewmodels.GameViewModel


@Composable
fun PokerDiceScreen(gvm: GameViewModel) {
    var isCurtainClosed by remember { mutableStateOf(false) }
    var isCurtainClosed2 by remember { mutableStateOf(true) }
    when(val state = gvm.state){
        is GameViewModel.State.Error -> TODO()
        GameViewModel.State.Idle -> TODO()
        GameViewModel.State.MatchInit -> {
            CommonLayout(
                bottomRow = {
                    RoundInitBottomRow(
                        onClick = {
                            isCurtainClosed = true
                        }
                    )
                },
            ) {}
            AnimatedCloseCurtain(
                isVisible = isCurtainClosed,
                onAnimationComplete = {
                    // Aqui você pode adicionar lógica para quando a animação terminar
                    // Por exemplo, mudar o estado do jogo
                    gvm.initializeRound()
                }
            )
        }
        is GameViewModel.State.PlayingInRound ->{

        }
    }
}

@Composable
private fun CommonLayout(
    bottomRow: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        topBar = { GameUpperBar() },
        floatingActionButton = { bottomRow() }
    ) { paddingValues ->
        GameScreenBox{
            PokerDiceTable{
                content(paddingValues)
            }

            AnimatedPlayersRow(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
