package isel.pdm.chelaspokerdice.ui.components.struct.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCard(content: @Composable () -> Unit){
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.9f)
            .padding(vertical = 8.dp),
        colors =  CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.6f),
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}