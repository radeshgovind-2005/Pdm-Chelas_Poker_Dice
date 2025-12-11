package isel.pdm.pokerdice.app

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import isel.pdm.pokerdice.services.AppMonitorService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppLifecycleMonitor(
    private val context: Context,
    private val logger: AppLog
) : DefaultLifecycleObserver {

    private var backgroundJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onStart(owner: LifecycleOwner) {
        backgroundJob?.cancel()
        logger.i("App entered Foreground: Stopping Monitor Service")
        context.stopService(Intent(context, AppMonitorService::class.java))
    }

    override fun onStop(owner: LifecycleOwner) {
        // This handles screen rotations or quick app switches gracefully.
        backgroundJob = scope.launch {
            delay(2000)
            logger.i("App entered Background (confirmed): Starting Monitor Service")

            val intent = Intent(context, AppMonitorService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Ensure your Service calls startForeground() immediately in its onCreate/onStartCommand
                try {
                    context.startForegroundService(intent)
                } catch (e: Exception) {
                    // Handle ForegroundServiceStartNotAllowedException on Android 12+
                    logger.e("Failed to start foreground service: ${e.message}")
                }
            } else {
                context.startService(intent)
            }
        }
    }
}