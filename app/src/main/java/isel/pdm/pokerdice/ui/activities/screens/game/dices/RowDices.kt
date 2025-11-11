package isel.pdm.pokerdice.ui.activities.screens.game.dices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.activities.screens.pd.WhiteDiceSquare

@Composable
fun RowDices() {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            WhiteDiceSquare()
        }
    }
}