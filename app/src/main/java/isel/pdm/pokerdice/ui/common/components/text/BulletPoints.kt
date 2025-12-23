package isel.pdm.pokerdice.ui.common.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BulletPoints(pointsResId: List<Int>) {
    Column(
        modifier = Modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        pointsResId.forEach { resId ->
            Text("• ${stringResource(resId)}")
        }
    }
}