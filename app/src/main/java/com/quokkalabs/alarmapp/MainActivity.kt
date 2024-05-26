package com.quokkalabs.alarmapp

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.quokkalabs.alarmapp.navigation.Screen
import com.quokkalabs.alarmapp.navigation.SetUpNavGraph
import com.quokkalabs.alarmapp.presentation.util.Helper
import com.quokkalabs.alarmapp.presentation.util.alertMessage
import com.quokkalabs.alarmapp.ui.theme.QuokkaLabsAlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG: String = this@MainActivity::class.java.simpleName
    private var keepSplashScreen = true;

    val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) keepSplashScreen = false
            else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionRequiredDialog()
            } else {
                showPermissionRequiredDialog()
                keepSplashScreen = true
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            keepSplashScreen = false

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            QuokkaLabsAlarmAppTheme {
                val navController = rememberNavController()
                SetUpNavGraph(
                    startDestination = Screen.HomeScreen.route,
                    navController = navController
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        stopAlarm()
    }

    private fun stopAlarm() {
        Helper.mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
            }
        }
    }

    private fun showPermissionRequiredDialog() {
        this.alertMessage(title = "Permission Required", message = "Please grant Notification permission to continue" ){
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuokkaLabsAlarmAppTheme {
        SetUpNavGraph(
            startDestination = Screen.HomeScreen.route,
            navController = rememberNavController()
        )
    }
}