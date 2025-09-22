package isel.pdm.chelaspokerdice.activities.title

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.chelaspokerdice.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreen.Structure(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal,
    context: Context,
    code: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Player Profile") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                HorizontalDivider()
                listOf(
                    "Player Profile",
                    "Language",
                    "Sound",
                    "Statistic",
                    "About"
                ).forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        selected = selectedItem == item,
                        onClick = {
                            selectedItem = item
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Chelas Poker Dice",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                Icons.Rounded.Menu,
                                contentDescription = "Menu",
                                tint = Color.White,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    },
                    actions = {
                        // PAra centrar o titulo
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Rounded.Settings,
                                contentDescription = null,
                                tint = Color.Transparent, // Make the icon invisible
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
            ) {
                Image(
                    painter = painterResource(R.drawable.simple_background),
                    contentDescription = "Logo Chelas Poker Dice",
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = alignment
                ) {
                    Image(
                        painter = painterResource(R.drawable.iconlight),
                        contentDescription = "Logo Chelas Poker Dice",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 32.dp)
                    )
                    Spacer(modifier.padding(100.dp))
                    code()
                }
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