package com.example.notificationactivity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar

class CloudMsgService : FirebaseMessagingService() {
    private val TAG = "FirebaseService"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var notificationData = message.notification
        print("notification title ${notificationData?.title}")
        print("notification body ${notificationData?.body}")
        print("notification data ${message.data}")

        //notification builder
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channels_name)
            val descriptionText = getString(R.string.description_text)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.

            notificationManager.createNotificationChannel(channel)
        }

         var builder = NotificationCompat.Builder(this, "CHANNEL_ID")
          .setSmallIcon(R.drawable.img)
        .setContentTitle(notificationData?.title?:"")
         .setContentText(notificationData?.body?:"")
        .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "refreshed token :$token")
    }

}
