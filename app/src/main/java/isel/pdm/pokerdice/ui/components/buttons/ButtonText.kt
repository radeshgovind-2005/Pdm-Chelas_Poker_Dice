package isel.pdm.pokerdice.ui.components.buttons


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.pokerdice.ui.components.icons.MyIcon

const val Button_Width = 200
const val TEXT_SIZE = 16
const val SPACER_WIDTH = 8
@Composable
fun ButtonText(
    text: String,
    icon: MyIcon? = null,
    iconDescrition: String? = null,
    modifier: Modifier = Modifier,
    color: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary
    ),
    enabled: Boolean = true,
    onClick: () -> Unit = {}
){
    Button(
        onClick = onClick,
        modifier = modifier.width(Button_Width.dp),
        enabled = enabled,
        colors = color
    ) {
        icon?.let{
            Icon(icon.value, contentDescription = iconDescrition)
            Spacer(modifier = Modifier.width(SPACER_WIDTH.dp))
        }
        Text(text, fontSize = TEXT_SIZE.sp,fontWeight = FontWeight.Medium)
    }
}