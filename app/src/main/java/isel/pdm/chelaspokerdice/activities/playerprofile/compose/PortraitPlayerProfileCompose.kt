package isel.pdm.chelaspokerdice.activities.playerprofile.compose


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.components.scaffold.DefaultScaffold


@Composable
fun PortraitPlayerProfileCompose(
    code: @Composable () -> Unit
) {
    DefaultScaffold("Player Profile", code = { code() })
}


@PreviewScreenSizes
@Composable
private fun Preview() {
    PortraitPlayerProfileCompose( { Button (onClick = {}){ Text("Ola") } } )
}
