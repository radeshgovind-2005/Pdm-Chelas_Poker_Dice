package isel.pdm.pokerdice.app

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import isel.pdm.pokerdice.services.AppMonitorService

class AppLifecycleMonitor(
    private val context: Context,
    private val logger: AppLog
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        logger.i("App entered Foreground: Stopping Monitor Service")
        context.stopService(Intent(context, AppMonitorService::class.java))
    }

    override fun onStop(owner: LifecycleOwner) {
        logger.i("App entered Background: Starting Monitor Service")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, AppMonitorService::class.java))
        }
    }
}