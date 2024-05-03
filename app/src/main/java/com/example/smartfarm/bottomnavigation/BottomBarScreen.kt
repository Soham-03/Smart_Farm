package com.example.smartfarm.bottomnavigation

import com.example.smartfarm.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "home",
        icon = R.drawable.home
    )

    object Chat : BottomBarScreen(
        route = "myFields",
        title = "My Fields",
        icon = R.drawable.ic_my_fields
    )

    object Notification : BottomBarScreen(
        route = "notifications",
        title = "Assistant",
        icon = R.drawable.ass
    )

}