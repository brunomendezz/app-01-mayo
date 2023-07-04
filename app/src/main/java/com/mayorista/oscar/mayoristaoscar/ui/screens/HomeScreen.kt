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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
    val bytesPDF by viewModel.pdfDocument.observeAsState()
    val productosEnOferta by viewModel.productosEnOferta.observeAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { Toolbar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {


            ContentHomeScreen(
                bytes = bytesPDF,
                onClick = { openPdf(bytesPDF!!, context) },
                onClickSucursal = { navController.navigate(route = AppScreens.MapScreen.route) },
                onClickVerTodos = { navController.navigate(route = AppScreens.OfertasScreen.route) })
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
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
        colors = topAppBarColors(Color(0xFFE0070F)),
        modifier = Modifier.shadow(0.dp)
    )
}


@Composable
fun ContentHomeScreen(
    bytes: ByteArray?,
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
                    endY = 50f
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
                        .background(Color.White)
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
fun CardPdf(bytes: ByteArray?, onClick: () -> Unit) {
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Sucursales",
                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        )
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación"
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Conoce nuestras distintas sucursales",
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }


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

        val targetHeight = 1900.dp // Altura para la vista previa
        val ratio = page.height.toFloat() / page.width.toFloat()
        val targetWidth = (targetHeight.value / ratio).toInt()

        val bitmap = Bitmap.createBitmap(
            targetWidth,
            targetHeight.value.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val renderQuality = PdfRenderer.Page.RENDER_MODE_FOR_PRINT //Calidad del render
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
        Card(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick() }
                .height(200.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = "No hay un PDF disponible por el momento, revise su conexion a internet e intente mas tarde",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun MarcaList() {
    val images = listOf(
        "https://media.taringa.net/knn/fit:550/Z3M6Ly9rbjMvdGFyaW5nYS8yLzQvMi84LzkvNy85MC9tYW5hb3NhcmcvREQ2LmpwZw",
        "https://www.exportadoresdecordoba.com/images_db/fotos/650px/17659_foto.jpg",
        "https://distribuidoraelcriollo.com/wp-content/uploads/2021/08/La-serenisima-portada.jpg",
        "https://i0.wp.com/www.sitemarca.com/wp-content/uploads/2017/05/Nada-como-llevar-Coca-Cola-a-tu-casa-1-e1494335411209.png?fit=600%2C293&ssl=1"
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imageUrl ->
            Card(
                modifier = Modifier
                    .width(250.dp)
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
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos en oferta",
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "Ver todos",
                        style = TextStyle(
                            fontSize = 15.sp,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { onClickVerTodos() }
                    )
                }
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(listaDeProductosEnOferta) { producto ->
                        ProductoCard(producto = producto)
                    }
                }
            }

        }
    }
}


@Composable
fun ProductoCard(producto: ProductoModel) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = producto.imagen),
                    contentDescription = "Imagen del producto",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = producto.nombre,
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


