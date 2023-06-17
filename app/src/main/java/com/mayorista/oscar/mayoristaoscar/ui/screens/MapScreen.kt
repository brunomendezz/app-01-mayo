package com.mayorista.oscar.mayoristaoscar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.mayorista.oscar.mayoristaoscar.data.model.BottomNavItem
import com.mayorista.oscar.mayoristaoscar.data.model.Marcador
import com.mayorista.oscar.mayoristaoscar.data.repos.MarcadorRepository
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel

@Composable
fun PantallaMapa(navController: NavController, viewModel: MainViewModel) {


    ViewContainer(navController, viewModel)

}

@Composable
fun MapScreen(currentUbication: LatLng) {

    val initialUbication = LatLng(-34.6690101, -58.5637967)
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.HYBRID)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings(rotationGesturesEnabled = false)) }

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
                .size(width = 380.dp, height = 500.dp)
                .padding(8.dp),
            cameraPositionState = cameraUpdate(initialUbication, currentUbication),
            properties = mapProperties,
            uiSettings = uiSettings
        ) {
            Marker(state = MarkerState(currentUbication))
        }
    }
}

private fun cameraUpdate(current : LatLng, next : LatLng) : CameraPositionState {

    if(current != next) {

        return CameraPositionState(position = CameraPosition(next, 16.6f, 0f, 0f))
    }

    return CameraPositionState(position = CameraPosition(current, 16.6f, 0f, 0f))
}
@Composable
fun ViewContainer(navController: NavController, viewModel: MainViewModel) {

    LocalContext.current.applicationContext
    var mUbicacionSeleccionada by remember {
        mutableStateOf(MarcadorRepository.ubicaciones[1])
    }

    Scaffold(
        topBar = { Toolbar(navController) },
        bottomBar = { Bottombar(navController, viewModel) }) {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


                mUbicacionSeleccionada =
                    selectorDeUbicacionesRegistradas(MarcadorRepository.ubicaciones, viewModel)



                MapScreen(mUbicacionSeleccionada.latLng)



                Spacer(modifier = Modifier.size(width = 0.dp, height = 5.dp))


        }
    }
}



@Composable
fun Bottombar(navController: NavController, viewModel: MainViewModel) {
    val bottomNavItem = listOf(

        BottomNavItem(
            name = "Home",
            route = "home_screen",
            icon = Icons.Rounded.Home
        )
    )

    NavigationBar(
        containerColor = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 60.dp)
    ) {


        bottomNavItem.forEach { item ->

            NavigationBarItem(
                selected = false,
                onClick = {
                    if (viewModel.screenUbication != item.route) {
                        when (item.route) {
                            "home_screen" -> navController.navigate(route = AppScreens.HomeScreen.route)
                            "map_screen" -> navController.navigate(route = AppScreens.MapScreen.route)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        }
    }
}



@Composable
private fun selectorDeUbicacionesRegistradas(listaMarcadores: List<Marcador>, viewModel: MainViewModel): Marcador {

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
