package com.quokkalabs.alarmapp.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.quokkalabs.alarmapp.data.local.AlarmEntity
import java.time.LocalDate
import java.time.LocalTime

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.component
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@Composable
fun AlarmItem(alarmEntity: AlarmEntity) {
    Column(
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 2.dp)
            .border(width = 2.dp, shape = RoundedCornerShape(5.dp), color = colorScheme.primary)
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(text = alarmEntity.title)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            DateTimeComponent(ItemType.DATE, date = alarmEntity.date)
            DateTimeComponent(ItemType.TIME, time = alarmEntity.time)
        }
    }
}

@Composable
@PreviewLightDark
fun PreviewAlarmItem() {
    Scaffold {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(5) {
                AlarmItem(
                    alarmEntity = AlarmEntity(
                        title = "New Alarm",
                        date = LocalDate.now(),
                        time = LocalTime.now()
                    )
                )
            }
        }
    }
}