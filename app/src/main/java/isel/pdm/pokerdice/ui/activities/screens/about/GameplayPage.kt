package isel.pdm.pokerdice.ui.activities.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.SimplePage

@Composable
fun GameplayPage(onWebRequest: (String) -> Unit) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.profile_p1_title) }
    val textResId by rememberSaveable { mutableIntStateOf(R.string.profile_p1_content) }
    val btnResId by rememberSaveable { mutableIntStateOf(R.string.profile_p1_btn) }
    SimplePage(titleResId){
        Column(Modifier.fillMaxSize()){
            Spacer(Modifier.height(32.dp))
            Text(stringResource(textResId))
            Spacer(Modifier.height(16.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = { onWebRequest("https://en.wikipedia.org/wiki/Poker_dice") },
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(stringResource(btnResId))
                }
            }
        }
    }
}