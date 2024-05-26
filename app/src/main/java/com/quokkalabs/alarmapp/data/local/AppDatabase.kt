package com.quokkalabs.alarmapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.data.local
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(
    value = [Converters::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlarmDao
}