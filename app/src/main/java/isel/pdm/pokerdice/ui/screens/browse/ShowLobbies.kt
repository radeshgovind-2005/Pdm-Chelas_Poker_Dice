package isel.pdm.pokerdice.ui.screens.browse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.model.lobby.BrowseLobby
import isel.pdm.pokerdice.ui.common.components.text.BoldTitle
import isel.pdm.pokerdice.ui.common.components.text.TitleSize

@Composable
fun ShowLobbies(lobbies: List<BrowseLobby>, onLobbyClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ){
        Spacer(Modifier.height(25.dp))
        lobbies.forEach { lobby ->
            Card(
                modifier=Modifier.fillMaxWidth().padding(horizontal=16.dp),
                shape = RoundedCornerShape(24.dp),
                colors =  CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ){
                Row(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable(onClick = {onLobbyClick(lobby.id.toString())})
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        BoldTitle(lobby.name, TitleSize.SMALL)
                        Row{
                            Text("${stringResource(R.string.browse_host)}: ${lobby.hostName}")
                            Text("\t|\t ${stringResource(R.string.browse_rounds)}: ${lobby.rounds} ")
                        }

                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Join"
                        )
                    }

                }
            }
        }
    }
}