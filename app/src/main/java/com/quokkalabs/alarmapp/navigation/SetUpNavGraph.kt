package com.quokkalabs.alarmapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quokkalabs.alarmapp.presentation.screen.HomeScreen

/**
 * Owner : Mandeep Singh
 * Package Name: com.quokkalabs.alarmapp.navigation
 * Project Name: Quokka Labs Alarm App
 * Created At: Sat, 25 May 2024
 **/

@Composable
fun SetUpNavGraph(
    startDestination: String,
    navController: NavHostController,

) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.HomeScreen.route){
            HomeScreen()
        }
    }
}