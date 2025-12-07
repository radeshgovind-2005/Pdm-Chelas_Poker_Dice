package isel.pdm.pokerdice.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCard(
    modifier: Modifier=Modifier.fillMaxSize(0.9f),
    sufVar: Float = 0.4f,
    content: @Composable () -> Unit
) {
    Column(
        modifier=Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier=modifier,
            shape = RoundedCornerShape(24.dp),
            colors =  CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = sufVar),
                contentColor = MaterialTheme.colorScheme.primary
            )
        ){
            content()
        }
    }

}