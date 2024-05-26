package com.quokkalabs.alarmapp.presentation.dialog

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarTimeline
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.quokkalabs.alarmapp.R
import com.quokkalabs.alarmapp.presentation.component.DateTimeComponent
import com.quokkalabs.alarmapp.presentation.component.ItemType
import com.quokkalabs.alarmapp.presentation.viewmodel.AlarmViewModel
import java.time.LocalDate
import java.time.LocalTime

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.dialog
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmDialog(
    onDismiss: () -> Unit,
    onSaveClicked: (String, LocalDate, LocalTime) -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val TAG: String = "AddAlarmDialog"
    val dateDialog = rememberSheetState()
    val timeDialog = rememberSheetState()

    CalendarDialog(state = dateDialog, selection = CalendarSelection.Date { localDate ->
        viewModel.updateAlarmDate(localDate)
        Log.d(TAG, "AddAlarmDialog() called with: localDate = $localDate")
        dateDialog.finish()
        timeDialog.show()

    }, config = CalendarConfig(disabledTimeline = CalendarTimeline.PAST))

    ClockDialog(state = timeDialog, selection = ClockSelection.HoursMinutes { hours, minutes ->
        viewModel.updateAlarmTime(LocalTime.of(hours, minutes))
        Log.d(TAG, "AddAlarmDialog() called with: hours = $hours, minutes = $minutes")
        viewModel.updateDateTimeUpdate(true)
        timeDialog.finish()
    })

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    stringResource(id = R.string.add_alarm),
                    style = TextStyle(fontSize = MaterialTheme.typography.headlineSmall.fontSize),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = viewModel.name.value,
                    onValueChange = { text -> viewModel.updateName(text) },

                    placeholder = { Text(text = stringResource(id = R.string.title)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(10),
                    maxLines = 1,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                ) {
                    DateTimeComponent(ItemType.DATE, date = viewModel.selectedDate.value)
                    DateTimeComponent(ItemType.TIME, time = viewModel.selectedTime.value)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                ) {
                    Button(onClick = {
                        onDismiss()
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    when (viewModel.dateTimeUpdated.value) {
                        true -> Button(onClick = {
                            onSaveClicked(
                                viewModel.name.value,
                                viewModel.selectedDate.value,
                                viewModel.selectedTime.value
                            )
                        }) {
                            Text(text = stringResource(id = R.string.add_alarm))
                        }

                        false -> Button(onClick = {
                            dateDialog.show()
                        }) {
                            Text(text = stringResource(id = R.string.date_time))
                        }
                    }
                }


            }
        }
    }


}


@Composable
@PreviewLightDark
fun PreviewAddAlarmDialog() {
    AddAlarmDialog(onDismiss = {}, onSaveClicked = { name, date, time -> })
}

