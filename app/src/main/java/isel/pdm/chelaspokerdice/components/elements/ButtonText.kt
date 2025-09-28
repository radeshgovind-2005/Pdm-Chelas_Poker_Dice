package isel.pdm.chelaspokerdice.components.elements

import android.media.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonText(
    text: String,
    icon: ImageVector? = null,
    iconDescrition: String? = null,
    modifier: Modifier = Modifier,
    onClick:  () -> Unit
) {
    Button(
        modifier = modifier.width(200.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        icon?.let{
            Icon(icon, contentDescription = iconDescrition)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text)
    }
}