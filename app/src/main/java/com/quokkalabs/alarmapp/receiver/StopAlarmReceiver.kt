package com.quokkalabs.alarmapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.quokkalabs.alarmapp.presentation.util.Helper

class StopAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Helper.mediaPlayer?.let {
            if(it.isPlaying){
                it.stop()
                it.release()
            }
        }
    }
}