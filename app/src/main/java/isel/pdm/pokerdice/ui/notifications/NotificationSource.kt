package isel.pdm.pokerdice.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import isel.pdm.pokerdice.NotificationLog
import isel.pdm.pokerdice.R

class NotificationSource(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "poker_dice_game_channel"
        private const val CHANNEL_NAME = "Game Updates"
        private const val CHANNEL_DESC = "Notifications about your turn and game starts"
    }

    fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500)
                setSound(null, null)
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun showGameStartedNotification(lobbyName: String) {
        NotificationLog.logDebug("show notification")
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Game Started!")
            .setContentText(" The match in '$lobbyName' has begun. Tap to play!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setSound(null)
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
        // .setContentIntent(pendingIntent)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())
    }
}