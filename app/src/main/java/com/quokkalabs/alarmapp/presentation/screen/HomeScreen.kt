package com.quokkalabs.alarmapp.presentation.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quokkalabs.alarmapp.R
import com.quokkalabs.alarmapp.data.local.AlarmEntity
import com.quokkalabs.alarmapp.presentation.component.AlarmItem
import com.quokkalabs.alarmapp.presentation.dialog.AddAlarmDialog
import com.quokkalabs.alarmapp.presentation.util.alertMessage
import com.quokkalabs.alarmapp.presentation.util.convertToString
import com.quokkalabs.alarmapp.presentation.viewmodel.AlarmViewModel
import kotlinx.coroutines.delay

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.presentation.screen
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val TAG: String = "HomeScreen"
    val context = LocalContext.current
    val pullRefreshState = rememberPullToRefreshState().apply {
        if (isRefreshing) {
            LaunchedEffect(true) {
                viewModel.getAllSavedRecords()
                delay(1500)
                endRefresh()
            }
        }
    }
    var showAddAlarmDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showAddAlarmDialog) {
        AddAlarmDialog(
            onDismiss = {
                showAddAlarmDialog = !showAddAlarmDialog
                viewModel.clearAllFields()
            },
            onSaveClicked = { name, date, time ->
                showAddAlarmDialog = !showAddAlarmDialog
                Log.d(
                    TAG,
                    "HomeScreen() called with: name = $name, date = ${date.convertToString()}, time = ${time.convertToString()}"
                )
                viewModel.insertNewAlarm(AlarmEntity(title = name, date = date, time = time))
            })
    }

    LaunchedEffect(Unit) {
        viewModel.getAllSavedRecords()
        Log.d(TAG, "HomeScreen() called: ${viewModel.savedAlarmList.value.toString()}")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    Icon(
                        Icons.TwoTone.Info,
                        "info",
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clickable {
                                context.alertMessage(
                                    "About Me",
                                    message = "Name: Mandeep Singh \nEmail: mandeepsinghpurwa1996@gmail.com"
                                ) {}
                            })
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddAlarmDialog = !showAddAlarmDialog }) {
                Icon(Icons.Filled.Add, contentDescription = "Add new Alarm")
            }
        }) {
        val scrollState = rememberLazyListState()
        val alarmList = viewModel.savedAlarmList.collectAsState()
        Box(
            modifier = Modifier
                .padding(it)
                .nestedScroll(pullRefreshState.nestedScrollConnection)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                state = scrollState
            ) {

                items(alarmList.value) {
                    AlarmItem(it)
                }
            }

            PullToRefreshContainer(
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen(viewModel = hiltViewModel())

}