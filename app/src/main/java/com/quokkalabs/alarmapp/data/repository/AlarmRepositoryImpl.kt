package com.quokkalabs.alarmapp.data.repository

import com.quokkalabs.alarmapp.data.local.AlarmDao
import com.quokkalabs.alarmapp.data.local.AlarmEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.data.local
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@Singleton
class AlarmRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmRepository {
    override suspend fun insert(alarmEntity: AlarmEntity) {
        withContext(Dispatchers.IO){
            alarmDao.insert(alarmEntity)
        }
    }

    override suspend fun update(alarmEntity: AlarmEntity) {
        withContext(Dispatchers.IO){
            alarmDao.update(alarmEntity)
        }
    }

    override suspend fun delete(alarmEntity: AlarmEntity) {
        withContext(Dispatchers.IO){
            alarmDao.delete(alarmEntity)
        }
    }

    @OptIn(FlowPreview::class)
    override suspend fun getAll(): Flow<List<AlarmEntity>> {
        return withContext(Dispatchers.IO){
            alarmDao.getAllAlarms().debounce(1000)
        }
    }


}