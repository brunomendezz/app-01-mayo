package com.mayorista.oscar.mayoristaoscar

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.FileProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import com.mayorista.oscar.mayoristaoscar.ui.screens.HomeScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.OfertasScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.PantallaMapa
import com.mayorista.oscar.mayoristaoscar.ui.screens.SplashScreen
import com.mayorista.oscar.mayoristaoscar.ui.theme.MayoristaOscarTheme
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MayoristaOscarTheme {
                // A surface container using the 'background' color from the theme
                Surface(color =MaterialTheme.colorScheme.background
                ) {
                   AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val bytesPDF by viewModel.pdfDocument.observeAsState()
        val vistaPreviaPDF by viewModel.vistaPreviaPDF.observeAsState(null)
        val productosEnOferta by viewModel.productosEnOferta.observeAsState()
        val context = this@MainActivity
        val packageManager = context.packageManager

        NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {

            composable(AppScreens.SplashScreen.route) {
                SplashScreen(navController = navController)
            }

            composable(AppScreens.HomeScreen.route) {
                viewModel.screenUbication = "home_screen"

                HomeScreen(
                    imageBitmap = vistaPreviaPDF,
                    onClickPDF = { openPdf(bytesPDF!!, context) },
                    onClickSucursal = { navController.navigate(route = AppScreens.MapScreen.route) },
                    onClickVerTodos = { navController.navigate(route = AppScreens.OfertasScreen.route) },
                    onClickFacebook = { abrirFacebook(context) },
                    onClickInstagram = { abrirInstagram(context, packageManager) }
                ) { abrirWhatsApp(context, packageManager) }
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

    private fun abrirFacebook(context: Context) {
        val facebookUrl = "https://www.facebook.com/mayoristaoscar"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        context.startActivity(intent)
    }


    private fun abrirInstagram(context: Context, packageManager: PackageManager) {
        val instagramUrl = "https://www.instagram.com/mayoristaoscar"
        val instagramAppUri = Uri.parse("https://instagram.com/_u/mayoristaoscar")

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = instagramAppUri
        intent.setPackage("com.instagram.android")

        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent)
        } else {

            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
            context.startActivity(webIntent)
        }
    }


    private fun abrirWhatsApp(context: Context, packageManager: PackageManager) {
        val phoneNumber = "+5491134126000"
        val whatsappWebUrl = "https://wa.me/$phoneNumber"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(whatsappWebUrl)
        intent.setPackage("com.whatsapp")


        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent)
        } else {

            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappWebUrl))
            context.startActivity(webIntent)
        }
    }

   private fun openPdf(bytes: ByteArray, context: Context) {
        val file = File(context.cacheDir, "Mayorista Oscar.pdf")
        file.writeBytes(bytes)

        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        context.startActivity(intent)
    }
}