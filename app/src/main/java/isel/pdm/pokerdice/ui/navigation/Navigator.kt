package isel.pdm.pokerdice.ui.navigation

import android.Manifest
import android.app.ActivityOptions
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import kotlin.getValue

abstract class NavActivity: ComponentActivity(){

    fun toScreen(destination: Class<*>, anim: Anim){
        val intent = Intent(this, destination)
        val animation = animation(anim)
        startActivity(intent, animation)
    }

    fun toClearScreen(
        destination: Class<*>,
        anim: Anim,
        intentConfig: (Intent) -> Unit = {}
    ){
        val intent = Intent(this, destination)
        intentConfig(intent)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        val animation = animation(anim)
        startActivity(intent, animation)
        finish()
    }

    fun toMail(sendTo: List<String>, subject: String){
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

    fun toWeb(uri: String){
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Could not access: $uri", Toast.LENGTH_LONG).show()
        }
    }


    private fun animation(anim: Anim): Bundle =
        ActivityOptions
            .makeCustomAnimation(this, anim.enter, anim.exit)
            .toBundle()

    sealed class Anim(val enter: Int, val exit: Int) {
        object Backwards : Anim(R.anim.slide_in_left, R.anim.slide_out_right)
        object Forward : Anim(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}