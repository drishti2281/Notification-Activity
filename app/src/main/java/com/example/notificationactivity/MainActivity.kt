package com.example.notificationactivity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationactivity.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val CHANNEL_ID = "Channel ID"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnshowNotification.setOnClickListener {
           sendNotification()
            showNotification()
        }
    }
    private fun showNotification(){
        var builder =NotificationCompat.Builder(this,CHANNEL_ID)
            .setOngoing(true)
           // .setStyle( NotificationCompat.BigTextStyle()
              //  .bigText("This is a large text notification. You can write a long text here to display in the notification. " +
                       // "This will allow users to read the entire message without expanding the notification."));
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis() + 5000)
        .setStyle(NotificationCompat.BigPictureStyle()
            .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img_1))
            .bigLargeIcon(null))
            .setAutoCancel(true)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())

    }
    private fun sendNotification(){
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Notification app")
            .setContentText("This is a notification message.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())

    }
  //  private fun showNotification(){
    //    var builder =NotificationCompat.Builder(this,CHANNEL_ID)
      //      .setOngoing(true)
            // .setStyle( NotificationCompat.BigTextStyle()
            //  .bigText("This is a large text notification. You can write a long text here to display in the notification. " +
            // "This will allow users to read the entire message without expanding the notification."));
   //         .setAutoCancel(true)
     //       .setWhen(System.currentTimeMillis() + 5000)
       //     .setStyle(NotificationCompat.BigPictureStyle()
         //       .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img_1))
           //     .bigLargeIcon(null))
            //.setAutoCancel(true)
      //  val notificationIntent = Intent(this, MainActivity::class.java)
       // val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
         //   PendingIntent.FLAG_UPDATE_CURRENT)
       // builder.setContentIntent(pendingIntent)

       // val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        //notificationManager.notify(1,builder.build())
   // }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channels_name)
            val descriptionText = getString(R.string.description_text)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}