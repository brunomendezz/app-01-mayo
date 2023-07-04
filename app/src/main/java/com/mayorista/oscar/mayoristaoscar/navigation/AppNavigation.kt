package com.mayorista.oscar.mayoristaoscar.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayorista.oscar.mayoristaoscar.ui.screens.HomeScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.OfertasScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.PantallaMapa
import com.mayorista.oscar.mayoristaoscar.ui.screens.SplashScreen
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {

        composable(AppScreens.SplashScreen.route) {
                SplashScreen(navController = navController)
        }

        composable(AppScreens.HomeScreen.route) {
            viewModel.screenUbication = "home_screen"
                HomeScreen(navController, viewModel)
        }

        composable(AppScreens.MapScreen.route) {
            viewModel.screenUbication = "map_screen"
                PantallaMapa(viewModel)
            }


        composable(AppScreens.OfertasScreen.route) {
            viewModel.screenUbication = "ofertas_screen"
                OfertasScreen()
            }
        }
    }


