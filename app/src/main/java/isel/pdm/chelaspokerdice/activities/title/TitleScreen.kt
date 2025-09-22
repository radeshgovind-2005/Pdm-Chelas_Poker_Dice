package isel.pdm.chelaspokerdice.activities.title


import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.activities.title.compose.PortraitTitleScreenCompose
import isel.pdm.chelaspokerdice.components.button.LandscapeButton
import isel.pdm.chelaspokerdice.components.button.PortraitButton
import isel.pdm.chelaspokerdice.components.screen.Screen


class TitleScreen(
    private val navMap: Map<(() -> Unit), Int>,
    private val context: Context
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        PortraitTitleScreenCompose {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                navMap.forEach {
                    PortraitButton(it.key, stringResource(it.value))
                }
            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        Structure(Modifier.padding(75.dp), Alignment.End, context = context) {
            navMap.forEach {
                LandscapeButton(it.key, stringResource(it.value))
            }
        }
    }
}


@PreviewScreenSizes()
@Composable
private fun Preview() {
    val map = mapOf({} to R.string.profile, {} to R.string.lobbies, {} to R.string.lobbies)
    TitleScreen(map, ComponentActivity()).Render(Modifier)
}



