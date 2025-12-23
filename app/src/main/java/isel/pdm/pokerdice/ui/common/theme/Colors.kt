package isel.pdm.pokerdice.ui.common.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color

val PokerBlack = Color(0xFF050505)
val SoberVariant = Color(0xFF1C1C1C)
val RedishBrown = Color(0xFF5D1C00)
val PokerWhite = Color(0xFFFFFFFF)
val PokerDarkWhite = Color(0xFFB0B0B0)
val PokerRedDeep = Color(0xFF220000)
val PokerRedVariant = Color(0xFF3E0505)
val HighlightDark=Color(0xFF333333)
val DarkerShadow=Color(0xFF000000)

val Background1=Color(0xFF3F0404)
val Background2=Color(0xFF3B1111)
val Background3=Color(0xFF540101)

val Curtain1 = Color(0xFF1C0000)
val Curtain2 = Color(0xFF440000)
val Curtain3 = Color(0xFF521E1E)
val Table1 = Color(0xFFA40000)
val Table2 = Color(0xFF640000)
val Table3 = Color(0xFF731717)

val backgroundBrush = Brush.radialGradient(
    colors = listOf(Background1, Background2, Background3),
    radius = 2200f
)
val tableBrush = Brush.radialGradient(
    colors = listOf(Table1, Table2, Table3),
    radius = 2200f
)

val curtainBrush = verticalGradient(
    colors = listOf(Curtain1,Curtain2,Curtain3),
    startY = 0f,
    endY = Float.POSITIVE_INFINITY
)