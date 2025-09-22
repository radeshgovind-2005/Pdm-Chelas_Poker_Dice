package isel.pdm.chelaspokerdice.activities.title

import android.widget.GridLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.chelaspokerdice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sketch(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal,
    code: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                        Text(
                            "Chelas Poker Dice",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                colors = TopAppBarColors(
                    Color.Black,
                    Color.White,
                    Color.Red,
                    Color.White,
                    Color.White
                )
                /*
                navigationIcon = ,
                actions = TODO(),
                expandedHeight = TODO(),
                windowInsets = TODO(),
                colors = TODO(),
                scrollBehavior = TODO()*/
            )
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding))
    }
}


@PreviewScreenSizes()
@Composable
private fun Preview() {
    Sketch(
        Modifier,
        Alignment.CenterHorizontally,
        @Composable
        {}
    )
}

fun main() {
    FontStyle.values().forEach { println(it) }
}