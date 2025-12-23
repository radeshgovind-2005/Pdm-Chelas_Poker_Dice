package isel.pdm.pokerdice.ui.screens.about

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.common.components.card.SimplePage
import isel.pdm.pokerdice.ui.common.components.text.BulletPoints

@Composable
fun ProjectPage(onMailClick: (List<String>, String) -> Unit) {
    val groupMembersIds = listOf(R.string.group_member1,R.string.group_member2, R.string.group_member3,)
    val emails = listOf(R.string.contact_mail1,R.string.contact_mail2,R.string.contact_mail3,)
    val contacts = emails.map{stringResource(it)}
    val subject = stringResource(R.string.mail_subject)
    SimplePage(R.string.profile_p2_title){
        Column(Modifier.fillMaxSize()){
            Spacer(Modifier.height(32.dp))
            Text(stringResource(R.string.profile_p2_content))
            BulletPoints(groupMembersIds)
            Spacer(Modifier.height(16.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = {
                        onMailClick(contacts,subject)
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(stringResource(R.string.profile_p2_btn))
                }
            }
        }
    }
}