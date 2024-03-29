package com.mayorista.oscar.mayoristaoscar.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.mayorista.oscar.mayoristaoscar.R
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HomeScreen(
    imageBitmap: ImageBitmap?,
    onClickPDF: () -> Unit,
    onClickSucursal: () -> Unit,
    onClickVerTodos: () -> Unit,
    onClickFacebook: () -> Unit,
    onClickInstagram: () -> Unit,
    onClickWathsApp: () -> Unit,
    onClickScannear: () -> Unit,
    onClickActualizarLista: () -> Unit,
    productosEnOferta: List<ProductoModel>?,
    onClickCerrarSesion:()-> Unit,
    listaActualizando: Boolean,
    ultimaActualizacion: String) {
    Scaffold(
        topBar = { Toolbar(onClickCerrarSesion) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (productosEnOferta != null) {
                ContentHomeScreen(
                    imageBitmap,
                    onClickPDF,
                    onClickSucursal,
                    onClickVerTodos,
                    onClickFacebook,
                    onClickInstagram,
                    onClickWathsApp,
                    onClickScannear,
                    onClickActualizarLista,
                    productosEnOferta,
                    listaActualizando,
                    ultimaActualizacion
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(onClickCerrarSesion:()->Unit) {
    var showDialog by remember { mutableStateOf(false) }

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
            actions = {
            IconButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp, tint = Color.White,
                    contentDescription = "Cerrar sesión"
                )
            }
        },
        colors = topAppBarColors(Color(0xFFE0070F)),
        modifier = Modifier.shadow(0.dp))

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                text = {
                    Text(text = "¿Está seguro que desea cerrar sesión?", textAlign = TextAlign.Center)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            onClickCerrarSesion()

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "Confirmar", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    ) {
                        Text(text = "Cancelar")
                    }
                })
        }
}


@Composable
fun ContentHomeScreen(
    imageBitmap: ImageBitmap?,
    onClick: () -> Unit,
    onClickSucursal: () -> Unit,
    onClickVerTodos: () -> Unit,
    onClickFacebook: () -> Unit,
    onClickInstagram: () -> Unit,
    onClickWathsApp: () -> Unit,
    onClickScannear: () -> Unit,
    onClickActualizarLista: () -> Unit,
    productosEnOferta: List<ProductoModel>,
    listaActualizando: Boolean,
    ultimaActualizacion: String

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
                    CardPdf(
                        imageBitmap = imageBitmap,
                        onClick = onClick,
                        onClickActualizarLista = onClickActualizarLista,
                        ultimaActualizacion = ultimaActualizacion,
                        listaActualizando = listaActualizando
                    )
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
           /* item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    CardProductosEnOferta(productosEnOferta,onClickVerTodos)
                }

            }*/

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    ScannearProducto(onClickScannear)
                }
            }


            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    Redes(
                        onClickFacebook = onClickFacebook,
                        onClickInstagram = onClickInstagram,
                        onClickWhatsApp = onClickWathsApp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannearProducto(onClickScannear: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = { onClickScannear() }
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
                            text = "Precio",
                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        )
                        androidx.compose.material.Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.lupascanner_logo),
                            contentDescription = "LectorQR"
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Escanea el código de barra de cualquier producto y obtené el precio!",
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }
        }
    }

}


@Composable
fun CardPdf(
    imageBitmap: ImageBitmap?,
    onClick: () -> Unit,
    onClickActualizarLista: () -> Unit,
    listaActualizando: Boolean,
    ultimaActualizacion: String
) {
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
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Precios",
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f) // Peso 1 para "Precios"
                    )

                    Text(
                        text = "Últ. act: $ultimaActualizacion",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {

                    if (listaActualizando) {
                        Box(modifier = Modifier.fillMaxWidth()
                            .height(220.dp)) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .padding(vertical = 15.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        MostrarVistaPreviaPDF(imageBitmap = imageBitmap) { onClick() }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
                            .clickable { onClickActualizarLista() },
                        textAlign = TextAlign.Center,
                    )
                }
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
                        text = "Conocé nuestras distintas sucursales",
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
            }
        }
    }
}


@Composable
fun MostrarVistaPreviaPDF(imageBitmap: ImageBitmap?, onClick: () -> Unit) {


    if (imageBitmap != null) {
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
                    .height(imageBitmap.height.dp),
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
    } else {
        Card(
            modifier = Modifier
                .padding(16.dp)
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
fun CardProductosEnOferta(productosEnOferta: List<ProductoModel>, onClickVerTodos: () -> Unit) {
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
                    items(productosEnOferta) { producto ->
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
                    painter = rememberAsyncImagePainter(model = R.mipmap.ic_mayooscar_foreground),
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
                    text = producto.descripcio,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(130.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

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
                    text = "Hasta: 29/08/2023",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}


@Composable
fun Redes(
    onClickFacebook: () -> Unit,
    onClickInstagram: () -> Unit,
    onClickWhatsApp: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        SocialIcon(
            painterResId = R.drawable.facebook_logo_blue,
            contentDescription = "Facebook",
            onClick = onClickFacebook
        )
        SocialIcon(
            painterResId = R.drawable.instagram_logo,
            contentDescription = "Instagram",
            onClick = onClickInstagram
        )
        SocialIcon(
            painterResId = R.drawable.wathsapp_logo,
            contentDescription = "WhatsApp",
            onClick = onClickWhatsApp
        )
    }
}

@Composable
fun SocialIcon(
    painterResId: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(painterResId)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(34.dp)
            .padding(8.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Fit
    )
}
@Composable
fun LoadingDialog(showDialog: Boolean) {
    AnimatedVisibility(visible = showDialog) {
        val loadingText = "Espere por favor.."
        AlertDialog(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(15.dp),
            onDismissRequest = { /* No hacer nada al tocar fuera del diálogo */ },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            title = {
                Box(modifier = Modifier
                    .height(105.dp)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .height(75.dp)
                            .width(75.dp)
                            .padding(8.dp)
                    )
                }


            },
            text = {
                Text(
                    text = loadingText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            },
            buttons = {}
        )
    }
}

@Composable
fun BarcodeInfoDialog(

    scannedValue: ProductoModel?,
    dataLoaded: Boolean,
    onClose: () -> Unit
) {
    AnimatedVisibility(visible = dataLoaded) {

        AlertDialog(
            modifier = Modifier.wrapContentSize(),
            onDismissRequest = onClose,
            title = {
                Text(
                    text = scannedValue?.descripcio ?: "",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(8.dp)
                )

            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Artículo: ${scannedValue?.cod_articu ?: ""}",
                        style = TextStyle(
                            fontSize = 20.sp,
                            textDecoration = TextDecoration.None,
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        textAlign = TextAlign.Center,
                        text = "$ ${scannedValue?.precio ?: ""}",
                        style = TextStyle(
                            fontSize = 35.sp,
                            textDecoration = TextDecoration.None,
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    onClick = onClose
                ) {
                    Text(color = Color.White, text = "Cerrar")
                }
            }
        )
    }
}

