package isel.pdm.chelaspokerdice.screens.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.lobby.struct.LobbyScaffold
import isel.pdm.chelaspokerdice.services.fakeservice.FakeLobbyService
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay
import isel.pdm.chelaspokerdice.vm.LobbyViewModel

const val LOBBY_SCAFFOLD_TAG = "lobby_scaffold"
const val LOBBY_CONTENT_TAG = "lobby_content"

class LobbyScreen(
    private val onNavigateBack: () -> Unit = {},
    private val onNavigateGame: () -> Unit = {},
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbyScreenContent()
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbyScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LobbyScreenContent() {
        LobbyScaffold(
            modifier = Modifier.testTag(LOBBY_SCAFFOLD_TAG),
            onClickMenu = {onNavigateBack()},
            onClickGame = {onNavigateGame()}
        ) { innerPadding ->
            ScrollableContentColumnDisplay(
                modifier = Modifier.testTag(LOBBY_CONTENT_TAG),
                innerPadding = innerPadding,
                vArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Lobby Header Card
                LobbyHeaderCard(
                    lobbyName = "Poker Night",
                    hostName = "Player1",
                    playerCount = "4/8",
                    rounds = "5 rounds"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Players List
                PlayersSection(
                    players = listOf(
                        Player("Player1", true, true),
                        Player("Player2", false, false),
                        Player("Player3", false, false),
                        Player("Player4", false, false),
                        Player("Waiting...", false, false),
                        Player("Waiting...", false, false),
                        Player("Waiting...", false, false),
                        Player("Waiting...", false, false)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Game Settings Card
                GameSettingsCard()

                Spacer(modifier = Modifier.height(16.dp))

                // Start Game Button
                Button(
                    onClick = onNavigateGame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start Game",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "START GAME",
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }
            }
        }
    }
}

@Composable
private fun LobbyHeaderCard(
    lobbyName: String,
    hostName: String,
    playerCount: String,
    rounds: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = lobbyName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LobbyInfoItem("Host", hostName)
                LobbyInfoItem("Players", playerCount)
                LobbyInfoItem("Rounds", rounds)
            }
        }
    }
}

@Composable
private fun LobbyInfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PlayersSection(players: List<Player>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Players",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                players.forEach { player ->
                    PlayerRow(player = player)
                }
            }
        }
    }
}

@Composable
private fun PlayerRow(player: Player) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Player",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(8.dp),
            tint = if (player.isHost) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (player.isHost) FontWeight.Bold else FontWeight.Normal,
                color = if (player.isReady) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
            if (player.isHost) {
                Text(
                    text = "Host",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (player.isReady && !player.isHost) {
            Text(
                text = "Ready",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun GameSettingsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Game Settings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            GameSettingItem("Game Mode", "Texas Hold'em")
            GameSettingItem("Blind Time", "60 seconds")
            GameSettingItem("Starting Chips", "1500")
            GameSettingItem("Max Players", "8")
        }
    }
}

@Composable
private fun GameSettingItem(setting: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = setting,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

data class Player(
    val name: String,
    val isHost: Boolean,
    val isReady: Boolean
)
