package isel.pdm.chelaspokerdice.ui.navigation

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import isel.pdm.chelaspokerdice.R

abstract class ActivityNavigator : ComponentActivity() {
     fun navigation(destination: Class<*>, anim: Anim){
        val intent = Intent(this, destination)
        val animation = animation(anim)
        startActivity(intent, animation)
    }

    fun navigation(uri: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private fun animation(anim: Anim): Bundle =
        ActivityOptions.makeCustomAnimation(this, anim.enter, anim.exit)
            .toBundle()

    sealed class Anim(val enter: Int, val exit: Int){
        object Backwards: Anim(R.anim.slide_in_left, R.anim.slide_out_right)
        object Forward: Anim(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}

