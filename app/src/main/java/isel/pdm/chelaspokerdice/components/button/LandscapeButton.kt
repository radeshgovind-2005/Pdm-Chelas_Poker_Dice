package isel.pdm.chelaspokerdice.components.button

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

@Composable
fun LandscapeButton(
    onClick: () -> Unit,
    stringRes: String
) {
    Button(
        modifier = Modifier.width(200.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(stringRes)
    }
}

@PreviewScreenSizes
@Composable
fun LandscapeButtonPreview(){
    PortraitButton({}, "Chelas Poker DiCeEeEeEe")
}