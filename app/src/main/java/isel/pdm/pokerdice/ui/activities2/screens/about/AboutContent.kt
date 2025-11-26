package isel.pdm.pokerdice.ui.activities.screens.about

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.components.text.BulletPoints
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.components.text.HyperLinkText
import isel.pdm.pokerdice.ui.components.text.PlainText


@Composable
fun AboutThisProjectContent(
    tb2: String,
    navToMail: (List<String>, String) -> Unit
) {
    val tabText = RememberString(R.string.tab_thisproject_text)
    val btnText = RememberString(R.string.tab_thisproject_button)
    val groupMembers = arrayOf(
        RememberString(R.string.tab_thisproject_member1),
        RememberString(R.string.tab_thisproject_member2),
        RememberString(R.string.tab_thisproject_member3),
    )
    val sendTo = listOf(
        RememberString(R.string.tab_thisproject_mail1),
        RememberString(R.string.tab_thisproject_mail2),
        RememberString(R.string.tab_thisproject_mail3),
    )
    val subject = RememberString(R.string.tab_thisproject_mail_subject)
    DefaultCard() {
        HeadingText(tb2)
        Spacer(modifier = Modifier.height(16.dp))
        PlainText(tabText)
        BulletPoints(groupMembers)
        Spacer(modifier = Modifier.height(50.dp))
        ButtonText(btnText, MyIcon.Email) {
            navToMail(sendTo, subject)
        }
    }
}

@Composable
fun AboutGameplayContent(tb1: String, navToWeb: (String) -> Unit) {
    val tabText = RememberString(R.string.tab_gameplay_text)
    val tabLinkText = RememberString(R.string.tab_gameplay_linktext)
    val link = RememberString(R.string.tab_gameplay_link)
    DefaultCard() {
        HeadingText(tb1)
        Spacer(modifier = Modifier.height(16.dp))
        PlainText(tabText)
        HyperLinkText(tabLinkText) { navToWeb(link) }
    }
}
