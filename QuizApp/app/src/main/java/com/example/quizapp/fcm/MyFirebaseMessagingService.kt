package com.example.quizapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val CHANNEL_ID = "Notification Channel"

    var i = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            createNotificationChannel()

            // Create an Intent for the activity you want to start
            val resultIntent = Intent(this, MainActivity::class.java)
                .putExtra("topic", remoteMessage.data["subject"])
                .putExtra("id", i)
// Create the TaskStackBuilder
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(resultIntent)
                // Get the PendingIntent containing the entire back stack
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            // Create an Intent for the activity you want to start
            val solveIntent = Intent(this, MainActivity::class.java)
                .putExtra("quiz", remoteMessage.data["quizName"])
                .putExtra("id", i)
// Create the TaskStackBuilder
            val solvePendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(solveIntent)
                // Get the PendingIntent containing the entire back stack
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }



            var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_sentiment_satisfied_black_24dp)
                .setContentTitle("New quiz")
                .setContentText("New quiz named ${remoteMessage.data["quizName"]} from subject ${remoteMessage.data["subject"]} is available.")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("New quiz named ${remoteMessage.data["quizName"]} from subject ${remoteMessage.data["subject"]} is available.")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_sentiment_satisfied_black_24dp, "Solve",
                    solvePendingIntent)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(i++, builder.build())
            }

        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHannel"
            val descriptionText = "Best channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}