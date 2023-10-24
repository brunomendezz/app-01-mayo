package com.mayorista.oscar.mayoristaoscar

import AuthScreen
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.integration.android.IntentIntegrator
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import com.mayorista.oscar.mayoristaoscar.ui.screens.BarcodeInfoDialog
import com.mayorista.oscar.mayoristaoscar.ui.screens.HomeScreen
import com.mayorista.oscar.mayoristaoscar.ui.screens.LoadingDialog
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MayoristaOscarTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                    AskNotificationPermission()
                    val resultScan by viewModel.infoProducto.observeAsState()
                    val showDialogProgress by viewModel.showDialogCircularProgres.observeAsState(
                        false
                    )
                    val dataLoaded by viewModel.dataLoaded.observeAsState(false)

                    LoadingDialog(showDialogProgress)

                    BarcodeInfoDialog(
                        scannedValue = resultScan,
                        dataLoaded = dataLoaded
                    ) {
                        viewModel.ondismisDialogProgress()
                    }
                }

            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val bytesPDF by viewModel.pdfDocument.observeAsState()
        val vistaPreviaPDF by viewModel.vistaPreviaPDF.observeAsState(null)
        val productosEnOferta = listOf(ProductoModel("1", "articulo de prueba", "2000000.0"))
        val context = this@MainActivity
        val packageManager = context.packageManager
        val ultimaActualizacion by viewModel.ultimaActualizacion.observeAsState("N/A")
        val listaActuliazando by viewModel.listaActualizando.observeAsState(false)

        NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {

            composable(AppScreens.SplashScreen.route) {
                SplashScreen(navController = navController)
            }

            composable(AppScreens.AuthScreen.route) {
                AuthScreen(navController)
            }

            composable(AppScreens.HomeScreen.route) {
                viewModel.screenUbication = "home_screen"

                HomeScreen(
                    imageBitmap = vistaPreviaPDF,
                    onClickPDF = { openPdf(bytesPDF!!, context) },
                    onClickSucursal = { navController.navigate(route = AppScreens.MapScreen.route) },
                    onClickVerTodos = { navController.navigate(route = AppScreens.OfertasScreen.route) },
                    onClickFacebook = { abrirFacebook(context) },
                    onClickInstagram = { abrirInstagram(context, packageManager) },
                    onClickWathsApp = { abrirWhatsApp(context, packageManager) },
                    onClickCerrarSesion = {
                        cerrarSesionAuth(navController)
                    },
                    onClickScannear = { initScanner() },
                    onClickActualizarLista = { actualizarLista() },
                    productosEnOferta = productosEnOferta,
                    listaActualizando = listaActuliazando,
                    ultimaActualizacion = ultimaActualizacion

                    )
            }

            composable(AppScreens.MapScreen.route) {
                viewModel.screenUbication = "map_screen"

                PantallaMapa(viewModel,
                    onClickCerrarSesion = {
                        cerrarSesionAuth(navController)
                    }

                )

            }


            composable(AppScreens.OfertasScreen.route) {
                viewModel.screenUbication = "ofertas_screen"
                productosEnOferta?.let { it1 ->
                    OfertasScreen(it1,
                        onClickCerrarSesion = {
                            cerrarSesionAuth(navController)
                        }
                    )
                }
            }
        }
    }

    private fun cerrarSesionAuth(navController: NavController) {
        navController.popBackStack()
        FirebaseAuth.getInstance().signOut()
        navController.navigate(route = AppScreens.AuthScreen.route)

    }

    private fun actualizarLista() {
        viewModel.actualizarListaDePrecios()
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


    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "Notificaciones activadas,las puedes desactivar desde ajustes.",
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction("Desactivar") {
                openAppSettings(this)
            }
            snackbar.show()
        } else {
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "Notificaciones desactivadas,las puedes activar desde ajustes.",
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction("Activar") {
                openAppSettings(this)
            }
            snackbar.show()
        }
    }

    private fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    private fun AskNotificationPermission() {
        val permissionGranted by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        var showDialog by remember { mutableStateOf(!permissionGranted) }

        if (!permissionGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    NotificationPermissionExplanationDialog(
                        visible = showDialog,
                        onAccept = {
                            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                            showDialog = false
                        },
                        onDecline = {
                            showDialog = false
                        }
                    )
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }


    @Composable
    fun NotificationPermissionExplanationDialog(
        visible: Boolean,
        onAccept: () -> Unit,
        onDecline: () -> Unit
    ) {
        if (visible) {
            AlertDialog(
                onDismissRequest = onDecline,
                title = {
                    Text(text = "Permiso de notificaciones")
                },
                text = {
                    Text(
                        text = "Al habilitar el permiso de notificaciones, podrás recibir notificaciones importantes en tiempo real."
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onAccept()
                        },
                    ) {
                        Text(text = "Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = onDecline
                    ) {
                        Text(text = "No, gracias")
                    }
                }
            )
        }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(
            IntentIntegrator.EAN_13,
            IntentIntegrator.EAN_8,
            IntentIntegrator.CODE_128,
            IntentIntegrator.CODE_39
        )
        integrator.setPrompt("Coloque el codigo de barras en el interior del rectangulo del visor para escanear")
        integrator.initiateScan()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            viewModel.getInfoProducto(result.contents)
            viewModel.showDialogProgress()
            Toast.makeText(this, "Código de barras: ${result.contents}", Toast.LENGTH_LONG).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}