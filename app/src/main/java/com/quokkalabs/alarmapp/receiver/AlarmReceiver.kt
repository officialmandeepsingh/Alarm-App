package com.quokkalabs.alarmapp.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import com.quokkalabs.alarmapp.R
import com.quokkalabs.alarmapp.presentation.util.showNotification

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.receiver
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val message = intent?.getStringExtra("title") ?:"title"
        Log.d("TAG", "onReceive() called with: Title: $message")
        context.showNotification(message)

    }
}