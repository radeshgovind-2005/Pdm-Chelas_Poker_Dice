package isel.pdm.pokerdice.app

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import isel.pdm.pokerdice.ui.activities.MainActivity

class AppMonitorService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "PokerDiceBackgroundChannel"
        val channelName = "Game Service"

        createNotificationChannel(channelId, channelName)
        val pendingIntent = intentOpenApp()

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Chelas Poker Dice")
            .setContentText("Game connection active in background")
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }

    private fun intentOpenApp(): PendingIntent? {
        // If the app is running, this Intent tells Android
        // to simply "bring the existing task to the front" without restarting anything.
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)

        if (launchIntent != null) {
            // These flags are standard for resuming tasks without altering their state
            launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED

            return PendingIntent.getActivity(
                this,
                0,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        // Fallback: If for some reason it can't find the launcher intent,
        // it reverts to the explicit call.
        val fallbackIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        return PendingIntent.getActivity(
            this,
            0,
            fallbackIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}