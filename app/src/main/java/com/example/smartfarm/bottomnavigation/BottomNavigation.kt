package com.example.smartfarm.bottomnavigation

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartfarm.ui.theme.DarkGreen
import com.example.smartfarm.ui.theme.LightGreen
import com.example.smartfarm.ui.theme.MyGray
import com.example.smartfarm.ui.theme.Purple40
import com.example.smartfarm.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(){
    val navController = rememberNavController()
//    val state by viewModel.state.collectAsState()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {it.calculateBottomPadding()
        BottomNavigationGraph(navController = navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(navController:NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Chat,
        BottomBarScreen.Notification
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MyGray),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        screens.forEach { screen->
            if(currentDestination!=null){
                AddItem(screen = screen, currentDestination =currentDestination , navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination,
    navController: NavHostController
){
    val selected = currentDestination.hierarchy.any{ it.route == screen.route }
    Box(
        modifier = Modifier
            .background(MyGray)
    ){
        Card(
            modifier = Modifier
                .height(40.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = if(selected){ Color(0xFF4ACA7D) } else{ MyGray }
            ),
        ){
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
            )
            {
                Icon(painter = painterResource(id = screen.icon),
                    tint = if(selected){Color.White}else{ Color.Gray },
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(40.dp, 40.dp)
                        .clickable {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                )
                AnimatedVisibility(visible = selected) {
                    Text(
                        text = screen.title,
                        fontSize = 14.sp,
                        color = if(selected){Color.White}else{ Color.Gray}
                    )
                }
            }
        }
    }

}
