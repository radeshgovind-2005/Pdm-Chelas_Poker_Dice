package isel.pdm.chelaspokerdice.screens.about.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.ui.components.elements.BulletPoints
import isel.pdm.chelaspokerdice.ui.components.elements.ButtonText
import isel.pdm.chelaspokerdice.ui.components.elements.SimpleText
import isel.pdm.chelaspokerdice.ui.components.elements.TitleText
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard
import isel.pdm.chelaspokerdice.R

const val GAME_RULES_LINK = "https://en.wikipedia.org/wiki/Poker_dice"
const val MAIL_SUBJECT = "Chelas Poker Dice App - Group 28"
val EMAILS = listOf(
    "A51620@alunos.isel.pt",
    "A51619@alunos.isel.pt",
    "A51618@alunos.isel.pt",
)
val GROUP_MEMBERS =
    arrayOf(
        "Radesh Govind",
        "Francisco Tavares",
        "Martim Monteiro",
    )

@Composable
fun AboutThisProject(onNavigateToMail: (List<String>, String) -> Unit) {
    SimpleCard {
        TitleText("About this Project")
        Spacer(modifier = Modifier.height(8.dp))
        SimpleText(stringResource(R.string.about_this_project))
        BulletPoints(GROUP_MEMBERS,50.dp)

        val context = LocalContext.current
        ButtonText(
            text = "Contact Us",
            icon = Icons.Default.Email,
            iconDescrition = "Contact via E-mail",
            modifier = Modifier.fillMaxWidth()
        ) {
            onNavigateToMail(EMAILS,MAIL_SUBJECT) // trocar para stringResource
        }
    }
}