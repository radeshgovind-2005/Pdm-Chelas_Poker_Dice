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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.SimplePage
import isel.pdm.pokerdice.ui.components.text.BulletPoints

@Composable
fun ProjectPage(onMailClick: (List<String>, String) -> Unit) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.profile_p2_title) }
    val textResId by rememberSaveable { mutableIntStateOf(R.string.profile_p2_content) }
    val btnResId by rememberSaveable { mutableIntStateOf(R.string.profile_p2_btn) }
    val subjectResId by rememberSaveable { mutableIntStateOf(R.string.mail_subject) }
    val groupMembersIds = remember {
        mutableStateListOf(
            R.string.group_member1,
            R.string.group_member2,
            R.string.group_member3,
        )
    }
    val emails = remember {
        mutableStateListOf(
            R.string.contact_mail1,
            R.string.contact_mail2,
            R.string.contact_mail3,
        )
    }
    val contacts = emails.map{stringResource(it)}
    val subject = stringResource(subjectResId)
    SimplePage(titleResId){
        Column(Modifier.fillMaxSize()){
            Spacer(Modifier.height(32.dp))
            Text(stringResource(textResId))
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
                    Text(stringResource(btnResId))
                }
            }
        }
    }
}