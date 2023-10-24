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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
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
fun PantallaMapa(viewModel: MainViewModel,
onClickCerrarSesion:()->Unit
                 ) {
    ViewContainerMap(viewModel, onClickCerrarSesion)
}

@Composable
fun MapScreen(currentUbication: Marcador) {
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings(rotationGesturesEnabled = false)) }
    val posicionCamara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUbication.latLng, 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = posicionCamara,
                    properties = mapProperties,
                    uiSettings = uiSettings
                ) {
                    LaunchedEffect(currentUbication) {
                        if (currentUbication.latLng != LatLng(0.0, 0.0))
                            posicionCamara.animate(CameraUpdateFactory.newLatLng(currentUbication.latLng))
                    }
                    Marker(state = MarkerState(currentUbication.latLng))
                }
            }

            BotonComoLlegar(mUbicacionSeleccionada = currentUbication)
        }
    }
}


@Composable
fun ViewContainerMap(viewModel: MainViewModel,
onClickCerrarSesion:()->Unit) {


    var mUbicacionSeleccionada by remember {
        mutableStateOf(MarcadorRepository.ubicaciones[1])
    }

    Scaffold(
        topBar = { Toolbar(onClickCerrarSesion) },
        bottomBar ={}
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                mUbicacionSeleccionada =
                    selectorDeUbicacionesRegistradas(MarcadorRepository.ubicaciones, viewModel)

                    MapScreen(mUbicacionSeleccionada)

            }


        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotonComoLlegar(mUbicacionSeleccionada: Marcador) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = {
            val ubicacion =
                "${mUbicacionSeleccionada.latLng.latitude},${mUbicacionSeleccionada.latLng.longitude}"
            val intentUri =
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$ubicacion")
            val intent = Intent(Intent.ACTION_VIEW, intentUri)
            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        },
    ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE0070F),Color(0xFFF9B603)),
                        startX = 0f,
                        endX = 1700f
                    ))) {
                    Text(
                        modifier = Modifier.align(Alignment.Center)
                            .padding(8.dp),
                        text = "¿Como llegar a ${mUbicacionSeleccionada.nombre}?",
                        style = TextStyle(fontSize = 25.sp),
                        textAlign = TextAlign.Center,
                        color = Color.White
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
                        viewModel.nuevaUbicacionSeleccionadaEnMapa(opcion)

                    },
                    text = { Text(text = opcion.nombre) })
            }
        }
    }
    return mSelectedUbi
}
