package isel.pdm.chelaspokerdice.activities.playerprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.chelaspokerdice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProfileScreen.PlayerProfileStructure(
    code: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Player Profile",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Rounded.Home,
                            contentDescription = "Home",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                },
                colors = TopAppBarColors(
                    Color.Black,
                    Color.Blue,
                    Color.Red,
                    Color.White,
                    Color.Black
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        )
        {
            Image(
                painter = painterResource(R.drawable.opa),
                contentDescription = "Logo Chelas Poker Dice",
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            code()
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    val map = mapOf(
        { } to R.string.title_screen,
    )
    PlayerProfileScreen(map).Render(Modifier)
}
