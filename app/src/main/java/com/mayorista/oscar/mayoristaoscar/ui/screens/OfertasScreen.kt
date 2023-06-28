package com.mayorista.oscar.mayoristaoscar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import org.w3c.dom.Text


@Composable
fun OfertasScreen(navController: NavHostController) {
    ViewContainer(navController)
}

@Composable
fun ViewContainer(navController: NavHostController) {
    val itemsPerRow = 2

    val listaDeProductosEnOferta = listOf(
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS86yckuT7u-t2WGFhdcuWR2PDRAJG1319Ttw&usqp=CAU"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://voglia.com.ar/wp-content/uploads/elementor/thumbs/pan1-pqsrnxlyoj8nct8ior5dnxt1cn85nleo8ik73j0yyo.png"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS86yckuT7u-t2WGFhdcuWR2PDRAJG1319Ttw&usqp=CAU"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://voglia.com.ar/wp-content/uploads/elementor/thumbs/pan1-pqsrnxlyoj8nct8ior5dnxt1cn85nleo8ik73j0yyo.png"
        ),  ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS86yckuT7u-t2WGFhdcuWR2PDRAJG1319Ttw&usqp=CAU"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://voglia.com.ar/wp-content/uploads/elementor/thumbs/pan1-pqsrnxlyoj8nct8ior5dnxt1cn85nleo8ik73j0yyo.png"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS86yckuT7u-t2WGFhdcuWR2PDRAJG1319Ttw&usqp=CAU"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://voglia.com.ar/wp-content/uploads/elementor/thumbs/pan1-pqsrnxlyoj8nct8ior5dnxt1cn85nleo8ik73j0yyo.png"
        ),  ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS86yckuT7u-t2WGFhdcuWR2PDRAJG1319Ttw&usqp=CAU"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://voglia.com.ar/wp-content/uploads/elementor/thumbs/pan1-pqsrnxlyoj8nct8ior5dnxt1cn85nleo8ik73j0yyo.png"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS86yckuT7u-t2WGFhdcuWR2PDRAJG1319Ttw&usqp=CAU"
        ),
        ProductoModel(
            "Pan voglia", descuento = 10, fechaExpiracion = "26/06/2023", precio = 350.0,
            imagen = "https://voglia.com.ar/wp-content/uploads/elementor/thumbs/pan1-pqsrnxlyoj8nct8ior5dnxt1cn85nleo8ik73j0yyo.png"
        ),
    )

    Scaffold(
        topBar = { Toolbar(navController) },
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
                        Modifier.fillMaxWidth()
                            .height(150.dp)
                    ){
                        Image(
                            painter = rememberAsyncImagePainter(model = producto.imagen),
                            contentDescription = "Imagen del producto",
                            contentScale= ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }

                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = producto.nombre,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "$${producto.precio}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Text(
                                text = "$${producto.precio - (producto.precio * producto.descuento / 100)}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Text(
                                text = "Hasta: ${producto.fechaExpiracion}",
                                style = TextStyle(fontSize = 14.sp),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
            }
        }
}



