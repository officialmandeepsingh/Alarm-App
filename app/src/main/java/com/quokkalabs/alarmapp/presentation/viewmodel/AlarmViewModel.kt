package com.quokkalabs.alarmapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quokkalabs.alarmapp.data.local.AlarmEntity
import com.quokkalabs.alarmapp.data.repository.AlarmRepository
import com.quokkalabs.alarmapp.receiver.AlarmReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject
import kotlin.random.Random

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.viewmodel
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val context: Context
) : ViewModel() {
    private var _name: MutableState<String> = mutableStateOf("")
    val name get() = _name

    private var _dateTimeUpdated: MutableState<Boolean> = mutableStateOf(false)
    val dateTimeUpdated get() = _dateTimeUpdated

    private var _selectedDate: MutableState<LocalDate> = mutableStateOf(LocalDate.now())
    val selectedDate get() = _selectedDate

    private var _selectedTime: MutableState<LocalTime> = mutableStateOf(LocalTime.now())
    val selectedTime get() = _selectedTime

    private var _savedAlarmList = MutableStateFlow(emptyList<AlarmEntity>())
    val savedAlarmList get() = _savedAlarmList

    private var _alarmManager: AlarmManager? = null

    init {
        _alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun updateName(alarmName: String) {
        _name.value = alarmName
    }

    fun updateAlarmDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun updateAlarmTime(time: LocalTime) {
        _selectedTime.value = time
    }

    fun updateDateTimeUpdate(staus: Boolean) {
        _dateTimeUpdated.value = staus
    }

    fun insertNewAlarm(alarmEntity: AlarmEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.insert(alarmEntity)
            scheduleAlarm(alarmEntity)
            clearAllFields()

        }
    }

    fun getAllSavedRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.getAll().collectLatest {
                Log.d("TAG", "getAllSavedRecords() called ${it.toString()}")
                _savedAlarmList.tryEmit(it)
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleAlarm(alertEntity: AlarmEntity) {
        val alarmTime = LocalDateTime.of(alertEntity.date, alertEntity.time)
            .atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(
                "title",
                alertEntity.title
            )
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Random.nextInt(10, 100),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        _alarmManager?.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)

    }

    fun clearAllFields() {
        _name.value = ""
        _selectedTime.value = LocalTime.now()
        _selectedDate.value = LocalDate.now()
        _dateTimeUpdated.value = false
    }

}
