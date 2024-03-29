package com.mayorista.oscar.mayoristaoscar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mayorista.oscar.mayoristaoscar.R
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    Splash()
    LaunchedEffect(key1 = true) {
        delay(1500)
        navController.popBackStack()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navController.navigate(AppScreens.HomeScreen.route)
        } else {
            navController.navigate(AppScreens.AuthScreen.route)
        }
    }

}

private @Composable
fun Splash() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(
                id = R.mipmap.ic_mayooscar_foreground,
            ),
            modifier = Modifier
                .width(245.dp)
                .height(245.dp),
            contentDescription = "Mayorista Oscar Logo"
        )
        Text(text = "Mayorista Oscar")

    }
}

