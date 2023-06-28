package com.mayorista.oscar.mayoristaoscar.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mayorista.oscar.mayoristaoscar.data.model.Marcador
import com.mayorista.oscar.mayoristaoscar.data.repos.MarcadorRepository
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel

@Composable
fun PantallaMapa(navController: NavController, viewModel: MainViewModel) {


    ViewContainer(navController, viewModel)

}

@Composable
fun MapScreen(currentUbication: LatLng) {

    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings(rotationGesturesEnabled = false)) }
    val posicionCamara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUbication, 15f)
    }

    Box(
        Modifier
            .padding(top = 5.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(shape = RoundedCornerShape(percent = 10))

    ) {
        GoogleMap(
            modifier = Modifier
                .size(width = 380.dp, height = 450.dp)
                .padding(8.dp),
            cameraPositionState = posicionCamara,
            properties = mapProperties,
            uiSettings = uiSettings
        ) {
            LaunchedEffect(currentUbication) {

                if (currentUbication != LatLng(0.0, 0.0))
                    posicionCamara.animate(CameraUpdateFactory.newLatLng(currentUbication))
            }
            Marker(state = MarkerState(currentUbication))
        }
    }
}

@Composable
fun ViewContainer(navController: NavController, viewModel: MainViewModel) {

    LocalContext.current.applicationContext
    var mUbicacionSeleccionada by remember {
        mutableStateOf(MarcadorRepository.ubicaciones[1])
    }

    Scaffold(
        topBar = { Toolbar(navController) },
        bottomBar = {}) {

        Box(modifier = Modifier.padding(it)){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                mUbicacionSeleccionada =
                    selectorDeUbicacionesRegistradas(MarcadorRepository.ubicaciones, viewModel)

                MapScreen(mUbicacionSeleccionada.latLng)

                BotonComoLlegar(mUbicacionSeleccionada)

            }
        }



    }
}

@Composable
fun BotonComoLlegar(mUbicacionSeleccionada: Marcador) {
    val context = LocalContext.current
    FloatingActionButton(
        modifier = Modifier.padding(16.dp),
        onClick = {
            val ubicacion = "${mUbicacionSeleccionada.latLng.latitude},${mUbicacionSeleccionada.latLng.longitude}"
            val intentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$ubicacion")
            val intent = Intent(Intent.ACTION_VIEW, intentUri)
            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        elevation = FloatingActionButtonDefaults.elevation(0.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFF9B603), Color(0xFFE0070F)),
                        startX = 900f,
                        endX = 425f
                    )
                )
                .padding(8.dp)
                .size(250.dp) // Tamaño aumentado del botón
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = "¿Cómo llegar a ${mUbicacionSeleccionada.nombre}?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}



@Composable
private fun selectorDeUbicacionesRegistradas(
    listaMarcadores: List<Marcador>,
    viewModel: MainViewModel
): Marcador {

    var mExpanded by remember { mutableStateOf(false) }

    val options: List<Marcador> = listaMarcadores

    val mSelectedUbi by viewModel.ubicacionMapa.observeAsState(initial = MarcadorRepository.ubicaciones[1])
    var mSelectedText by remember { mutableStateOf(listaMarcadores[0].nombre) }

    var mTextFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)) {

        OutlinedTextField(
            value = mSelectedUbi.nombre,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            readOnly = true,
            label = {
                Text(
                    "Sucursales"
                )
            },
            trailingIcon = {
                Icon(icon, "expanded icon",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {

            options.forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        mSelectedText = opcion.nombre
                        mExpanded = false
                        // mSelectedUbi = opcion
                        viewModel.nuevaUbicacionSeleccionadaEnMapa(opcion)

                    },
                    text = { Text(text = opcion.nombre) })
            }
        }
    }
    return mSelectedUbi
}
