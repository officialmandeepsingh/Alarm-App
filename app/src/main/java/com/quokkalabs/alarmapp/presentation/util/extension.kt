package com.quokkalabs.alarmapp.presentation.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.util
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@SuppressLint("SimpleDateFormat")
fun Long.convertMillisToDate() : String{
    val formatter = SimpleDateFormat("dd/MMM/yyyy")
    return formatter.format(this)
}

fun LocalDate.convertToString(): String{
    val formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
    return formatter.format(this)
}

fun LocalTime.convertToString(): String{
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return formatter.format(this)
}

fun Context.alertMessage(title: String, message: String, positiveBtnText: String? = "Okay", action: ()-> Unit){
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveBtnText){dialog,_ ->
            dialog.dismiss()
            action()
        }.show()
}
