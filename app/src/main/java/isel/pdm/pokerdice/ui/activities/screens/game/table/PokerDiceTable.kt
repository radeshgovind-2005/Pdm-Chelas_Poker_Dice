package isel.pdm.pokerdice.ui.activities.screens.game.table

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.layout.ColumnTopCenter
import isel.pdm.pokerdice.ui.components.text.CursiveTitle
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun PokerDiceTable(content: @Composable () -> Unit) {
    DesignedPokerTable{
        ColumnTopCenter {
            CursiveTitle(RememberString(R.string.game_name))
            content()
        }
    }
}

@Composable
private fun DesignedPokerTable(content: @Composable () -> Unit){
    PokerTableShadow(0.96f,295.dp)
    Table(275.dp){
        PokerTableSurface{
            content()
        }
    }
    TableHighlight(0.88f,290.dp)

}

@Composable
private fun Space(){Spacer(modifier = Modifier.height(50.dp))}