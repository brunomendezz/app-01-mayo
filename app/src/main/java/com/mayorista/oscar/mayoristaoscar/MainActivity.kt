package com.mayorista.oscar.mayoristaoscar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mayorista.oscar.mayoristaoscar.navigation.AppNavigation
import com.mayorista.oscar.mayoristaoscar.ui.screens.HomeScreen
import com.mayorista.oscar.mayoristaoscar.ui.theme.MayoristaOscarTheme
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MayoristaOscarTheme {
                // A surface container using the 'background' color from the theme
                Surface(color =MaterialTheme.colorScheme.background
                ) {
                   AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}