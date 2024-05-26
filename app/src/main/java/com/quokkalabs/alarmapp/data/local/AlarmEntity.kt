package com.quokkalabs.alarmapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.data.local
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: LocalDate,
    val time: LocalTime
)
