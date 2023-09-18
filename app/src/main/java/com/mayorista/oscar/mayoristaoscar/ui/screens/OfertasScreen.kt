package com.mayorista.oscar.mayoristaoscar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mayorista.oscar.mayoristaoscar.R
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel


@Composable
fun OfertasScreen(listaDeProductosEnOferta: List<ProductoModel>,onClickCerrarSesion: () -> Unit) {
    ViewContainer(listaDeProductosEnOferta, onClickCerrarSesion )
}

@Composable
fun ViewContainer(listaDeProductosEnOferta: List<ProductoModel>,
onClickCerrarSesion:()->Unit) {
    val itemsPerRow = 2


    Scaffold(
        topBar = { /*Toolbar(onClickCerrarSesion)*/ },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(itemsPerRow),
            modifier = Modifier.padding(it)
        ) {
            items(listaDeProductosEnOferta) { producto ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.elevatedCardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {

                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = R.drawable.mayooscar_logo1),
                            contentDescription = "Imagen del producto",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }

                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = producto.descripcio,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = "$${producto.precio}",
                            style = TextStyle(
                                fontSize = 14.sp,
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = "$${producto.precio.toDouble() - (producto.precio.toDouble() * 10 / 100)}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = "Hasta: 28/08/2023",
                            style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}



