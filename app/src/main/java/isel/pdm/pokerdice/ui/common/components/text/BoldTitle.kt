package isel.pdm.pokerdice.ui.common.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TitleSize(val size: TextUnit){
    SMALL(20.sp),
    DEFAULT(25.sp),
    LARGE(27.sp),
}
@Composable
fun BoldTitle(title: String, tSize: TitleSize = TitleSize.DEFAULT) {
    Text(title, fontWeight = FontWeight.ExtraBold, fontSize=tSize.size)
}