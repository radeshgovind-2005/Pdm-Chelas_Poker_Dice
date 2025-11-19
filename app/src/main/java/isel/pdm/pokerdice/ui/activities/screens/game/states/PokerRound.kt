package isel.pdm.pokerdice.ui.activities.screens.game.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.activities.screens.game.GameMainLayout
import isel.pdm.pokerdice.ui.activities.screens.game.effects.OpenCurtain
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.text.HeadingLevel
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.theme.DarkWhite
import isel.pdm.pokerdice.ui.theme.PureBlack

@Composable
fun PokerRound() {
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
        bottomRow = {},
        tableContent = {},
        screenContent = {
            if(infoBoxOpen)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DefaultCard(
                        modifier = Modifier.fillMaxSize(0.85f),
                        hca = Alignment.CenterHorizontally,
                        sufVar = 1f
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ){
                            HeadingText("GAME TITLE", color = DarkWhite)
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ){
                            HeadingText("Round: x/10", HeadingLevel.H3,color = DarkWhite)
                            HeadingText("Highest Hand: J J J J J (Full House)", HeadingLevel.H3,color = DarkWhite)
                            HeadingText("Active Players: Joao, Maria", HeadingLevel.H3,color = DarkWhite)
                            HeadingText("Ante: 100$", HeadingLevel.H3,color = DarkWhite)

                        }
                    }
                }
        }
    )
    OpenCurtain()
}