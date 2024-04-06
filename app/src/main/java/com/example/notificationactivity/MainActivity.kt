package com.example.notificationactivity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.R.attr.path
import android.R.attr.privateImeOptions
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.resources.Compatibility.Api18Impl.setAutoCancel
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.notificationactivity.databinding.ActivityMainBinding
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val CHANNEL_ID = "Channel ID"
    val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fabbutton.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.click_notification)
            var etEmail = dialog.findViewById<EditText>(R.id.etEmail)
            var etPassword = dialog.findViewById<EditText>(R.id.etPassword)
            var btnclicknotification = dialog.findViewById<Button>(R.id.btnclicknotification)


            btnclicknotification.setOnClickListener {
                if (etEmail.text.toString().trim().isNullOrEmpty()) {
                    etEmail.error = "enter your email"
                } else if (etPassword.text.toString().trim().isNullOrEmpty()) {
                    etPassword.error = "enter your password"
                } else {
                    dialog.dismiss()
                }
            }
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        binding.btnshowNotification.setOnClickListener {
            showNotification()
        }
        binding.btnimageNotification.setOnClickListener {
            sendImageNotification()
        }
        binding.btncancelNotification.setOnClickListener {
            noCancelNotification()
        }
        binding.btnlargetextNotification.setOnClickListener {
            largetextNotification()
        }
        binding.btnautoCancelAfterSometimes.setOnClickListener {
            autoCancelAfterSometimes()
        }
        binding.btngenerateNotification.setOnClickListener {
            generateNotification()
        }
    }

    private fun sendImageNotification() {

        createNotificationChannel()

        var imageIcon =
            "https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg"
        var bitmap: Bitmap? = null

        Glide.with(this).asBitmap()
            .load(imageIcon)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource

                    var builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                        .setSmallIcon(R.drawable.img)
                        .setContentTitle("Notification app")
                        .setContentText("This is a notification message.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setOngoing(true)
                        //large style
                        .setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText(
                                    "This is a large text notification. You can write a long text here to display in the notification. "
                                )
                        )
                        .setWhen(System.currentTimeMillis() + 5000)
                        .setStyle(
                            NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)

                        )
                        .setAutoCancel(true)

                    notificationManager.notify(1, builder.build())
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channels_name)
            val descriptionText = getString(R.string.description_text)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.img_1, null)
        val bitmapDrawable = drawable as BitmapDrawable
        val largeicon = bitmapDrawable.bitmap
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Notification text")
            .setContentText("This is a notification message.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            //large style
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "This is a large text notification. You can write a long text here to display in the notification. "
                    ))
            .setWhen(System.currentTimeMillis() + 5000)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img_1))
                    .bigLargeIcon(largeicon)
            )
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())

    }
    private fun noCancelNotification(){
        var builder= NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Notification tools")
            .setContentText("This is a notification message.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setAutoCancel(true)
        notificationManager.notify(1,builder.build())

    }
    private fun largetextNotification(){
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.img_1, null)
        val bitmapDrawable = drawable as BitmapDrawable
        val largeicon = bitmapDrawable.bitmap
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Notification text")
            .setContentText("this is notification text image")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(largeicon)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("This will allow users to read the entire message without expanding the notification."))
            .setAutoCancel(true)
        notificationManager.notify(1,builder.build())
    }
    private fun autoCancelAfterSometimes(){
        var builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Notificarion code")
            .setContentText("This is a large text notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setWhen(System.currentTimeMillis() + 5000)
            .setAutoCancel(true)
        notificationManager.notify(1,builder.build())

    }
    private fun generateNotification(){
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("data", "data")
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP )
        var pendingIntent = PendingIntent.getActivity(this, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        var builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.img)
            .setContentTitle("Notificarion code")
            .setContentText("This is a large text notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        notificationManager.notify(1,builder.build())
    }
}
