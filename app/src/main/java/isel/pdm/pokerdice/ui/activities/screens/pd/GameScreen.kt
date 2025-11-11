package isel.pdm.pokerdice.ui.activities.screens.pd

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import kotlin.math.cos
import kotlin.math.sin

data class Player(
    val id: Int,
    val name: String,
    val isCurrentTurn: Boolean = false
)
@Composable
fun GameScreen() {
    val players = listOf(
        Player(1, "P1", isCurrentTurn = true),
        Player(2, "P2"),
        Player(3, "P3"),
        Player(4, "P4"),
        Player(5, "P5"),
        Player(6, "P6")
    )

    // State to control when the animation should run
    var shouldAnimate by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Left corner - Balance
                Column(verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = "Balance: $1500,00",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Center - Roll button
                Column(verticalArrangement = Arrangement.Bottom) {
                    ButtonText("Roll All Dices",
                        color = ButtonDefaults.buttonColors(
                            containerColor = Color(0x99FFFFFF),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                        // Start the animation when button is clicked
                        shouldAnimate = true
                    })
                }

                // Right corner - Hand info
                Column(verticalArrangement = Arrangement.Bottom) {
                    Text(
                        text = "  Hand: - - - - -",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
        val p = padding
        GameBackground() {
            // Shadow effect
            TableShadow()
            // Main table with realistic border
            MainTable {
                // Felt surface
                TableSurface {
                    // Modern discreet title
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TableTitle()
                        // Center row with 5 white squares
                        Spacer(modifier = Modifier.height(50.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(horizontal = 32.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(5) {
                                WhiteDiceSquare()
                            }
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                        Text("Highest Hand: JJJAAA", color = Color.Black)
                        Text("(Full House)", color = Color.Black)
                    }
                }
            }
            TableInnerHightlIght()
        }
        // Pass the animation state to the Effect composable
        Effect(shouldAnimate = shouldAnimate, onAnimationComplete = { shouldAnimate = false })
        PlayerBox()
    }
}

@Composable
fun Effect(shouldAnimate: Boolean, onAnimationComplete: () -> Unit) {
    val transition = updateTransition(targetState = shouldAnimate, label = "dice_animation")

    val animationProgress by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                tween(durationMillis = 4000, easing = LinearEasing)
            } else {
                snap()
            }
        },
        label = "dice_animation_progress"
    ) { isAnimating ->
        if (isAnimating) 1f else 0f
    }

    // Check if animation completed
    LaunchedEffect(animationProgress) {
        if (animationProgress >= 1f && shouldAnimate) {
            onAnimationComplete()
        }
    }

    // Parâmetros da animação
    val radius = 150.dp
    val startDiceIndex = 2 // Começa do dado do meio (índice 2)
    val startPosition = when (startDiceIndex) {
        0 -> Offset(-90.dp.value, 0f)
        1 -> Offset(-45.dp.value, 0f)
        2 -> Offset(0f, 0f)
        3 -> Offset(45.dp.value, 0f)
        4 -> Offset(90.dp.value, 0f)
        else -> Offset(0f, 0f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Calcular a posição baseada no progresso da animação
        val currentAngle = 360f * animationProgress
        val radians = Math.toRadians(currentAngle.toDouble())

        // Posição final no círculo
        val circleX = (cos(radians) * radius.value).toFloat()
        val circleY = (sin(radians) * radius.value).toFloat()

        // Interpolar entre a posição inicial (dado existente) e a posição no círculo
        val currentX = startPosition.x + (circleX - startPosition.x) * animationProgress
        val currentY = startPosition.y + (circleY - startPosition.y) * animationProgress

        Box(
            modifier = Modifier
                .offset(x = currentX.dp, y = currentY.dp)
                .size(45.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("•", color = Color.Black, fontSize = 24.sp)
        }
    }
}
@Composable
fun PlayerBox(){
    val row1 = 250
    val row2 = 150
    Box(
        modifier = Modifier
            .offset(x = 540.dp, y = 235.dp)
            .size(80.dp)
    ){PlayerContent("Xande",turn=true)}

    Box(
        modifier = Modifier
            .offset(x = 685.dp, y = 185.dp)
            .size(80.dp)
    ){PlayerContent("Chico")}

    Box(
        modifier = Modifier
            .offset(x = 120.dp, y = 220.dp)
            .size(80.dp)
    ){PlayerContent("Martim")}
    Box(
        modifier = Modifier
            .offset(x = 250.dp, y = 235.dp)
            .size(80.dp)
    ){PlayerContent("Gui")}

    Box(
        modifier = Modifier
            .offset(x = 10.dp, y = 175.dp)
            .size(80.dp)
    ){PlayerContent("Andre")}
}

@Composable
fun PlayerContent(username: String,turn:Boolean=false){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(turn)
            SimpleIcon(MyIcon.Player, tint=Color(0xFFFFDF00))
        else
            SimpleIcon(MyIcon.Player)
        Text(username, color = Color.White)
    }
}
@Composable
fun WhiteDiceSquare() {
    Box(
        modifier = Modifier
            .size(45.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .drawWithCache {
                onDrawBehind {
                    // Add a subtle shadow
                    drawRect(
                        color = Color(0x40000000),
                        topLeft = Offset(2f, 2f),
                        size = size.copy(width = size.width - 4f, height = size.height - 4f)
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // You can add dice content here later
        Text(
            text = "•",
            color = Color.Black,
            fontSize = 24.sp
        )
    }
}

@Composable
fun PlayerIconsAroundCurvedTable(players: List<Player>) {
    // Top curved row (3 players)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp) // Closer to top curve
            .padding(horizontal = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        players.take(3).forEach { player ->
            PlayerIcon(
                player = player,
                modifier = Modifier.size(48.dp)
            )
        }
    }

    // Bottom curved row (3 players)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 260.dp) // Positioned on bottom curve
            .padding(horizontal = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        players.drop(3).take(3).forEach { player ->
            PlayerIcon(
                player = player,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun PlayerIcon(
    player: Player,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Player circle with subtle shadow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    if (player.isCurrentTurn) Color(0xFFFFD700) else Color(0xFF2F4F4F)
                )
                .drawWithCache {
                    onDrawBehind {
                        // Subtle shadow effect
                        drawCircle(
                            color = Color(0x40000000),
                            radius = size.width / 2 + 2,
                            center = center
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // Modern turn indicator - subtle gold ring
        if (player.isCurrentTurn) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.95f)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .drawWithCache {
                        onDrawBehind {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFFD700),
                                        Color(0x00FFD700)
                                    ),
                                    center = center,
                                    radius = size.width / 2
                                ),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                            )
                        }
                    }
            )
        }
    }
}