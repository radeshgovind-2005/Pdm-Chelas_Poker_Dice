package isel.pdm.chelaspokerdice.ui.navigation

import android.app.ActivityOptions
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.net.toUri
import isel.pdm.chelaspokerdice.R

abstract class ActivityNavigator : ComponentActivity() {
    fun navigationToScreen(destination: Class<*>, anim: Anim) {
        val intent = Intent(this, destination)
        val animation = animation(anim)
        startActivity(intent, animation)
    }

    fun navigationToWeb(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Could not access: $uri", Toast.LENGTH_LONG).show()
        }
    }

    fun navigationToMail(emails: List<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, emails.toTypedArray())
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }

        try {
            startActivity(Intent.createChooser(intent, "Send Email"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No email app available", Toast.LENGTH_LONG).show()
        }
    }

    private fun animation(anim: Anim): Bundle =
        ActivityOptions.makeCustomAnimation(this, anim.enter, anim.exit)
            .toBundle()

    sealed class Anim(val enter: Int, val exit: Int) {
        object Backwards : Anim(R.anim.slide_in_left, R.anim.slide_out_right)
        object Forward : Anim(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}


