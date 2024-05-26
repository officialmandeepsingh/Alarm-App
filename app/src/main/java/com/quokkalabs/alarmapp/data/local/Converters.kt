package com.quokkalabs.alarmapp.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.data.local
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/
class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String = time.toString()

    @TypeConverter
    fun toLocalDate(date: String): LocalDate = LocalDate.parse(date)

    @TypeConverter
    fun fromLocalTime(time: String): LocalTime = LocalTime.parse(time)


}