package isel.pdm.pokerdice.ui.components.icons


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

const val ICON_SIZE = 35
sealed class MyIcon(val value: ImageVector){
    data object Drawer : MyIcon(Icons.Default.Menu)
    data object Back : MyIcon(Icons.AutoMirrored.Filled.ArrowBack)
    data object Email : MyIcon(Icons.Default.Email)
    data object Last : MyIcon(Icons.Default.Refresh)
    data object Close: MyIcon(Icons.Default.Close)
    data object Search: MyIcon(Icons.Default.Search)
    data object Join: MyIcon(Icons.AutoMirrored.Filled.KeyboardArrowRight)
    data object Create: MyIcon(Icons.Default.Add)
    data object Start: MyIcon(Icons.Default.PlayArrow)
    data object Player: MyIcon(Icons.Default.Person)
    data object Logout: MyIcon(Icons.AutoMirrored.Filled.ExitToApp)
    data object Error: MyIcon(Icons.Default.Warning)
}

@Composable
fun SimpleIcon(
    icon: MyIcon,
    horizontalPadding: Int = 0,
    modifier: Modifier = Modifier,
    size: Int = ICON_SIZE,
    ){
    Icon(
        imageVector = icon.value,
        contentDescription = icon.toString(),
        tint = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .size(size.dp)
            .padding(horizontalPadding.dp)
    )
}
@Composable
fun SimpleButtonIcon(icon: MyIcon, onClick: () -> Unit){
    IconButton(onClick= {onClick()}){
        SimpleIcon(icon)
    }
}