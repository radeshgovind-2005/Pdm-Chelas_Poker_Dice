package isel.pdm.pokerdice.ui.components.error

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun DefaultErrorContent(
    message: String,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onRetry: (() -> Unit)? = null
){
    OneFullColumn {
        DefaultCard(modifier = modifier.padding(padding)){
            SimpleIcon(MyIcon.Error)
            HeadingText(message)
            onRetry?.let { retryAction ->
                Spacer(modifier = Modifier.height(32.dp))
                ButtonText(
                    text = RememberString(R.string.retry_button),
                    onClick = retryAction
                )
            }
        }
    }
}