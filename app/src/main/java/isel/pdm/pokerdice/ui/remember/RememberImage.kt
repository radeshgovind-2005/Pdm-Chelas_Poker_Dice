package isel.pdm.pokerdice.ui.remember

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RememberImage(
    resId: Int,
    description: String = "",
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
    ) {
    var id by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            resId
        }.let{
            id = it
        }
    }

    id?.let { id ->
        Image(
            painter = painterResource(id),
            contentDescription = description,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}