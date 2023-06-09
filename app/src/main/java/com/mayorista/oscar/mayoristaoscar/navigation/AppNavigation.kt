package com.mayorista.oscar.mayoristaoscar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayorista.oscar.mayoristaoscar.ui.screens.HomeScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.PantallaMapa
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination =AppScreens.HomeScreen.route){
        composable(route = AppScreens.HomeScreen.route){
            viewModel.screenUbication = "home_screen"
            HomeScreen(navController)
        }
        composable(route = AppScreens.MapScreen.route){
            viewModel.screenUbication = "map_screen"
            PantallaMapa(navController,viewModel)
        }


    }
}