package isel.pdm.chelaspokerdice.activities.about.struct

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

fun openEmailClient(context: Context, emails: Array<String>, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, emails)
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No email app available", Toast.LENGTH_LONG).show()
    }
}
