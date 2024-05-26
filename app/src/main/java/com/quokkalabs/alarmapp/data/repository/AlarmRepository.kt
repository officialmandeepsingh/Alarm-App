package com.quokkalabs.alarmapp.data.repository

import com.quokkalabs.alarmapp.data.local.AlarmEntity
import kotlinx.coroutines.flow.Flow

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.data.local
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/
interface AlarmRepository {
    suspend fun insert(alarmEntity: AlarmEntity)
    suspend fun update(alarmEntity: AlarmEntity)
    suspend fun delete(alarmEntity: AlarmEntity)
    suspend fun getAll(): Flow<List<AlarmEntity>>
}