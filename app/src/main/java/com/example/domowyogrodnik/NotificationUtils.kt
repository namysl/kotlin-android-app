package com.example.domowyogrodnik

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class  NotificationUtils(base: Context, plant_photo: String?, plant_name: String?, chore: String?):
    ContextWrapper(base){
    val plantname = plant_name
    val photo = plant_photo
    val chore = chore
    val MYCHANNEL_ID = "App Alert Notification ID"
    val MYCHANNEL_NAME = "App Alert Notification"

    private var manager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }

    // Create channel for Android version 26+
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        val channel = NotificationChannel(MYCHANNEL_ID, MYCHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.enableVibration(true)

        getManager().createNotificationChannel(channel)
    }

    // Get Manager
    fun getManager() : NotificationManager {
        if (manager == null) manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return manager as NotificationManager
    }

    fun getNotificationBuilder(): NotificationCompat.Builder {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val icon = BitmapFactory.decodeResource(this.resources, R.drawable.logo)

        val myBitmap = BitmapFactory.decodeFile(photo+"/profile.jpg")
        return NotificationCompat.Builder(applicationContext, MYCHANNEL_ID)
            .setContentTitle(plantname)
            .setContentText(chore)
            .setSmallIcon(R.drawable.ic_plants)
            .setLargeIcon(myBitmap)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(myBitmap).bigLargeIcon(null)
            )
            .setColor(Color.RED)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
    }

    private fun loadImageFromStorage(path: String, img: ImageView?){
        try{
            val f = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            img!!.setImageBitmap(b)
        }
        catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }
}