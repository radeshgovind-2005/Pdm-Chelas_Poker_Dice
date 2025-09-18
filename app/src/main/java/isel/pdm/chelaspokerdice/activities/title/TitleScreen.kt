package isel.pdm.chelaspokerdice.activities.title


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.components.MainButton

/***
 * Screen:
 *   - Portrait ( vertical)
 *   - Landscape ( horizontal )
 *   cada screen tem navbar + mainContent
 *    - PRreview
 *    Nota otimizar os 3 mainButton
 */

@Composable
fun TitleScreen(
    navToProfile: () -> Unit,
    navToLobbies: () -> Unit,
    navToAbout: () -> Unit,
) {
    val viewType = LocalConfiguration.current.orientation
    if(viewType == Configuration.ORIENTATION_LANDSCAPE){
        TitleLandscapeView({navToProfile()},{navToLobbies()},{navToAbout()})
    }else{// Configuration.ORIENTATION_PORTRAIT
        TitleLandscapeView({navToProfile()},{navToLobbies()},{navToAbout()})
    }


}


@Preview(showSystemUi = true)
@Composable
fun TitleScreenLandscapePreview() {
    TitleLandscapeView ({ }, { }, { })
}
