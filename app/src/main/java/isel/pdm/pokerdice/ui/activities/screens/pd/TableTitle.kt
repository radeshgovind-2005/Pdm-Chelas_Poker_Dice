package isel.pdm.pokerdice.ui.activities.screens.pd

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableTitle(){
    Text(
        text = "CHELAS POKER DICE",
        color = Color(0x99FFFFFF),
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Cursive,
        modifier = Modifier.padding(top = 30.dp),
        letterSpacing = 2.sp
    )
}