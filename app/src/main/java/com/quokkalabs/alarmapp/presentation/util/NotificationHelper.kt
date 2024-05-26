package com.quokkalabs.alarmapp.presentation.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.quokkalabs.alarmapp.MainActivity
import com.quokkalabs.alarmapp.R
import com.quokkalabs.alarmapp.receiver.StopAlarmReceiver
import kotlin.random.Random

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.util
 * Project Name: Quokka Labs Alarm App
 * Created At: Sun, 26 May 2024
 **/


fun Context.showNotification(message: String) {
    var notificationManager: NotificationManager? = null
    val soundUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext,soundUri).apply {
        isLooping = false

    }
    Helper.mediaPlayer = mediaPlayer
    mediaPlayer.start()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = this.getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(getString(R.string.app_name), name, importance).apply {
            description = message
        }

        notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    val notificationIntent = Intent(this, MainActivity::class.java).apply {
        action = Helper.ACTION_STOP_ALARM
    }
    val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


    val stopAlarmIntent = Intent(this, StopAlarmReceiver::class.java)


    val alarmPendingIntent = PendingIntent.getBroadcast(
        this,
        Random.nextInt(10, 100),
        stopAlarmIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )


    var builder = NotificationCompat.Builder(this, this.getString(R.string.app_name))
        .setSmallIcon(R.drawable.baseline_notifications_active_24)
        .setContentTitle(this.getString(R.string.app_name))
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .addAction(R.drawable.baseline_stop_circle_24,"Stop", alarmPendingIntent)
        .setSound(soundUri)
        .setVibrate(longArrayOf(0, 500, 1000))
        .build()

    notificationManager?.notify(Random.nextInt(10,100), builder)

}


