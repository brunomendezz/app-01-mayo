package com.mayorista.oscar.mayoristaoscar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayorista.oscar.mayoristaoscar.ui.screens.HomeScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.PantallaMapa
import com.mayorista.oscar.mayoristaoscar.ui.screens.SplashScreen
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination =AppScreens.SplashScreen.route){
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(route = AppScreens.HomeScreen.route){
            viewModel.screenUbication = "home_screen"
            HomeScreen(navController,viewModel)
        }
        composable(route = AppScreens.MapScreen.route){
            viewModel.screenUbication = "map_screen"
            PantallaMapa(navController,viewModel)
        }


    }
}