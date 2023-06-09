package com.mayorista.oscar.mayoristaoscar.navigation

import androidx.compose.runtime.Composable

sealed class AppScreens(val route:String){
    object HomeScreen:AppScreens("home_screen")
    object MapScreen:AppScreens("map_screen")

}
