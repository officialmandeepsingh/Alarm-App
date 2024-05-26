package com.quokkalabs.alarmapp.navigation

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.navigation
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
}
