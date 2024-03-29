package com.mayorista.oscar.mayoristaoscar.navigation

import androidx.compose.runtime.Composable

sealed class AppScreens(val route:String){
    object HomeScreen:AppScreens("home_screen")
    object MapScreen:AppScreens("map_screen")
    object OfertasScreen:AppScreens("ofertas_screen")

    object SplashScreen:AppScreens("splash_screen")

    object AuthScreen:AppScreens("auth_screen")

}
