package com.quokkalabs.alarmapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.data.local
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/
@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmEntity: AlarmEntity)

    @Delete
    suspend fun delete(alarmEntity: AlarmEntity)

    @Update
    suspend fun update(alarmEntity: AlarmEntity)

    @Query("select * from alarm")
    fun getAllAlarms() : Flow<List<AlarmEntity>>
}