package isel.pdm.pokerdice.ui.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.net.toUri

fun Activity.navigateTo(
    dest: Class<*>,
    finish: Boolean=true,
    intentConfig: Intent.() -> Unit = {}
){
    val intent = Intent(this, dest)
    intent.intentConfig()
    startActivity(intent)
    if(finish)
        finish()
}

fun Activity.navigateToMail(sendTo: List<String>, subject: String){
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, sendTo.toTypedArray())
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }

    try {
        startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "No email app available", Toast.LENGTH_LONG).show()
    }
}


fun Activity.navigateToWeb(uri: String){
    val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "Could not access: $uri", Toast.LENGTH_LONG).show()
    }
}
fun Activity.requestNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }
}