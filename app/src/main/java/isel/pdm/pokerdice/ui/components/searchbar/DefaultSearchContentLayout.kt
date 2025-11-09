package isel.pdm.pokerdice.ui.components.searchbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.components.card.DefaultCard

@Composable
fun <R> DefaultSearchContentLayout(
    items: List<R>,
    onClick: (R) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (R) -> Unit
){
    LazyColumn {
        items(items){ item ->
            DefaultCard{
                Row(
                    modifier = modifier
                        .clickable {onClick(item)}
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    content(item)
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}