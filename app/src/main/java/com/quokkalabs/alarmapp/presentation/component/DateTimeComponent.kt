package com.quokkalabs.alarmapp.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.quokkalabs.alarmapp.presentation.util.convertToString
import java.time.LocalDate
import java.time.LocalTime

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.component
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/


enum class ItemType{DATE, TIME}

@Composable
fun DateTimeComponent(itemType: ItemType, date:LocalDate? = null, time: LocalTime? = null){
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = when(itemType){
            ItemType.DATE -> Icons.TwoTone.DateRange
            ItemType.TIME -> Icons.Filled.Schedule
        }, contentDescription = "Date", tint = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.width(5.dp))
        when(itemType){
            ItemType.DATE -> date?.let { Text(it.convertToString()) }
            ItemType.TIME -> time?.let { Text(it.convertToString()) }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewDateTimeComponent(){
    Row {
        DateTimeComponent(ItemType.DATE, date = LocalDate.now())
        DateTimeComponent(ItemType.TIME, time = LocalTime.now())
    }
}