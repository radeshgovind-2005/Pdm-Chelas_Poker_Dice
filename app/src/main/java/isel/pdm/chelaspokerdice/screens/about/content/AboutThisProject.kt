package isel.pdm.chelaspokerdice.screens.about.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.about.struct.openEmailClient
import isel.pdm.chelaspokerdice.ui.components.elements.BulletPoints
import isel.pdm.chelaspokerdice.ui.components.elements.ButtonText
import isel.pdm.chelaspokerdice.ui.components.elements.SimpleText
import isel.pdm.chelaspokerdice.ui.components.elements.TitleText
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard


const val GAME_RULES_LINK = "https://en.wikipedia.org/wiki/Poker_dice"
const val ABOUT_THIS_PROJECT = "This application was developed as part of the Mobile Devices Programming (PDM) course " +
        "at Instituto Superior de Engenharia de Lisboa (ISEL), for the Computer Engineering and " +
        "Computers degree (LEIC).\n\n" +
        "Developed during the 2025/2026 Winter Semester by Group 28, class 52D:"
const val MAIL_SUBJECT = "Chelas Poker Dice App - Group 28"
val EMAILS = arrayOf(
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
fun AboutThisProject() {
    SimpleCard {
        TitleText("About this Project")
        Spacer(modifier = Modifier.height(8.dp))
        SimpleText(text = ABOUT_THIS_PROJECT)
        BulletPoints(GROUP_MEMBERS,50.dp)

        val context = LocalContext.current
        ButtonText(
            text = "Contact Us",
            icon = Icons.Default.Email,
            iconDescrition = "Contact via E-mail",
            modifier = Modifier.fillMaxWidth()
        ) {
            openEmailClient(
                context,
                EMAILS,
                MAIL_SUBJECT
            )
        }
    }
}