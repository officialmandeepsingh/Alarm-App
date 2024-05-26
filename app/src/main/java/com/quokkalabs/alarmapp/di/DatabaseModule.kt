package com.quokkalabs.alarmapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.quokkalabs.alarmapp.data.local.AlarmDao
import com.quokkalabs.alarmapp.data.repository.AlarmRepository
import com.quokkalabs.alarmapp.data.repository.AlarmRepositoryImpl
import com.quokkalabs.alarmapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.di
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, "Alarm"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(myDb: AppDatabase): AlarmDao {
        return myDb.alertDao()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao): AlarmRepository {
        return AlarmRepositoryImpl(alarmDao)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}