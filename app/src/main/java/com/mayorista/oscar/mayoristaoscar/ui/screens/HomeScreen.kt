package com.mayorista.oscar.mayoristaoscar.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.mayorista.oscar.mayoristaoscar.R
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
    val bytes by viewModel.pdfDocument.observeAsState()
    val productosEnOferta by viewModel.productosEnOferta.observeAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { Toolbar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {


            bytes?.let { it1 ->
                ContentHomeScreen(
                    bytes = it1,
                    onClick = { openPdf(bytes!!, context) },
                    onClickSucursal = { navController.navigate(route = AppScreens.MapScreen.route) },
                    onClickVerTodos = { navController.navigate(route = AppScreens.OfertasScreen.route) })
            }

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(navController: NavController) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mayorista Oscar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier.padding(end = 2.dp)
                )
            }
        },
        colors = topAppBarColors(
            MaterialTheme.colorScheme.primary
        )/*TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue)*/,
        actions = {
            TopAppBarActionButton(
                imageVector = Icons.Default.LocationOn,
                description = "Ubications Icon",
                onClick = { navController.navigate(route = AppScreens.MapScreen.route) }
            )
        },
        modifier = Modifier.shadow(0.dp)
    )
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = description,
            tint = Color.White,
            modifier = Modifier.size(35.dp)
        )
    }
}


@Composable
fun ContentHomeScreen(
    bytes: ByteArray,
    onClick: () -> Unit,
    onClickSucursal: () -> Unit,
    onClickVerTodos: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0070F), Color(0xFFFFFFFF)),
                    startY = 0f,
                    endY = 150f
                )
            )
    ) {
        LazyColumn(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFE0070F), Color(0xFFFFFFFF)),
                                startY = 0f,
                                endY = 200f
                            )
                        )
                ) {
                    CardPdf(bytes = bytes) {
                        onClick()
                    }

                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    MarcaList()
                }

            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    NuestrasSucursalesCard(onClickSucursal)
                }

            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    CardProductosEnOferta(onClickVerTodos)
                }

            }


        }


    }


}



@Composable
fun CardPdf(bytes: ByteArray, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {


        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Text(
                text = "Precios",
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth(), thickness = 1.dp
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                MostrarVistaPreviaPDF(pdfByteArray = bytes) { onClick() }
            }

            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {

                Text(
                    text = "Abrir PDF",
                    style = TextStyle(
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline,
                    ),
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onClick() },
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Actualizar lista",
                    style = TextStyle(
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline,
                    ),
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { },
                    textAlign = TextAlign.Center,
                )

            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuestrasSucursalesCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFF9B603), Color(0xFFE0070F)),
                        startX = 200f,
                        endX = 900f
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sucursales",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Conoce nuestras distintas sucursales",
                    style = TextStyle(fontSize = 15.sp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Ubicación",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}


@Composable
fun MostrarVistaPreviaPDF(pdfByteArray: ByteArray?, onClick: () -> Unit) {
    if (pdfByteArray != null) {
        val file = File.createTempFile("temp", ".pdf")
        val outputStream = FileOutputStream(file)
        outputStream.write(pdfByteArray)
        outputStream.close()

        val parcelFileDescriptor =
            ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(parcelFileDescriptor)
        val page = renderer.openPage(0)

        val targetHeight = 1900.dp // Altura deseada para la vista previa
        val ratio = page.height.toFloat() / page.width.toFloat()
        val targetWidth = (targetHeight.value / ratio).toInt()

        val bitmap = Bitmap.createBitmap(
            targetWidth,
            targetHeight.value.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val renderQuality = PdfRenderer.Page.RENDER_MODE_FOR_PRINT // Cambio de modo de renderizado
        val renderRect = Rect(0, 0, targetWidth, targetHeight.value.toInt())
        page.render(bitmap, renderRect, null, renderQuality)

        val imageBitmap = bitmap.asImageBitmap()

        Card(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick() }
                .height(200.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(targetHeight),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop, // Escala para ajustar el ancho de la imagen
                    alignment = Alignment.TopCenter
                )
            }
        }

        page.close()
        renderer.close()
        parcelFileDescriptor.close()
        file.delete()
    } else {
        Text(text = "No hay una lista de precio disponible.")
    }
}

@Composable
fun MarcaList() {
    val images = listOf(
        "https://media.taringa.net/knn/fit:550/Z3M6Ly9rbjMvdGFyaW5nYS8yLzQvMi84LzkvNy85MC9tYW5hb3NhcmcvREQ2LmpwZw",
        "https://i.ytimg.com/vi/q3DQcUu7HGg/maxresdefault.jpg",
        "https://distribuidoraelcriollo.com/wp-content/uploads/2021/08/La-serenisima-portada.jpg",
        "https://scontent.faep4-2.fna.fbcdn.net/v/t39.30808-6/301604648_457380146428086_4454130853595389542_n.png?_nc_cat=109&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=uFMGWBRi7TEAX9Hixsv&_nc_ht=scontent.faep4-2.fna&oh=00_AfA1R0xuTnsj5ZPZvaKNnMMgBIMrrv71NjgdeUAfIA0mgQ&oe=64993652",
        "https://scontent.faep4-2.fna.fbcdn.net/v/t39.30808-6/218133258_2893189577664095_1744972515755886442_n.png?_nc_cat=109&ccb=1-7&_nc_sid=e3f864&_nc_ohc=ABA0JmNScV0AX9b2uhL&_nc_ht=scontent.faep4-2.fna&oh=00_AfDs2R1Qb7WVbk6ZQVYKwqgpm4ZlPwqbms2H_nS3NJcbAA&oe=64991396",
        "https://i0.wp.com/www.sitemarca.com/wp-content/uploads/2017/05/Nada-como-llevar-Coca-Cola-a-tu-casa-1-e1494335411209.png?fit=600%2C293&ssl=1"
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imageUrl ->
            Card(
                modifier = Modifier
                    .width(250.dp) // Establece el ancho de la tarjeta al máximo disponible
                    .height(110.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    val painter = rememberAsyncImagePainter(imageUrl)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    if (painter.state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardProductosEnOferta(onClickVerTodos: () -> Unit) {
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
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .height(275.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos en oferta",
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Ver todos",
                        style = TextStyle(
                            fontSize = 15.sp,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Red,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onClickVerTodos() },
                        textAlign = TextAlign.Center,
                    )
                }


                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            }
        }



        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 50.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            ) {


                items(listaDeProductosEnOferta) { producto ->
                    ProductoCard(producto = producto)
                }
            }
        }


    }
}


@Composable
fun ProductoCard(producto: ProductoModel) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {


        Row() {

            Image(
                painter = rememberAsyncImagePainter(model = producto.imagen),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .width(150.dp)
                    .height(75.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .align(alignment = Alignment.CenterVertically)

            )

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


fun openPdf(bytes: ByteArray, context: Context) {
    val file = File(context.cacheDir, "lista de precios.pdf")
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


