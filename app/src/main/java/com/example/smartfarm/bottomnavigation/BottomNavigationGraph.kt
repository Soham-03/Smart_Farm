package com.example.smartfarm.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarm.screens.ChatScreen
import com.example.smartfarm.screens.HomeScreen
import com.example.smartfarm.screens.NotificationScreen
import com.example.smartfarm.screens.SelectedField
import kotlinx.coroutines.delay

@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Chat.route){
            NotificationScreen()
        }
        composable(route = BottomBarScreen.Notification.route){
            ChatScreen()
        }
    }
}