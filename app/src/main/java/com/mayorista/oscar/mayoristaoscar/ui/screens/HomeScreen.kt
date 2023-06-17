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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mayorista.oscar.mayoristaoscar.navigation.AppScreens
import com.mayorista.oscar.mayoristaoscar.ui.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
    val bytes by viewModel.pdfDocument.observeAsState()
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
                ContentHomeScreen(bytes = it1) {
                    openPdf(bytes!!, context)
                }
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
            Color.Red
        )/*TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue)*/,
        actions = {
            TopAppBarActionButton(
                imageVector = Icons.Rounded.LocationOn,
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
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0070F), Color(0xFFFFFFFF)),
                    startY = 0f,
                    endY = 200f
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
                        .fillMaxSize()
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
                MercadoPagoCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                )
            }
            item {
                MercadoPagoCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                )
            }
            item {
                MercadoPagoCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                )
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                MostrarVistaPreviaPDF(pdfByteArray = bytes) { onClick() }
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text(text = "Actualizar lista de precios", color = Color.Red)
            }
        }

    }
}

@Composable
fun MercadoPagoCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(text = "CADA VEZ MAS CERCA! SERA QUE SERA POSIBLE ESTE OBJETIVO?? DIGAMEN ALGOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO O O OO O O OO O OO OO O O O ")
            Spacer(modifier = Modifier.width(16.dp))

            Spacer(modifier = Modifier.weight(1f))
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

        val targetHeight = 400.dp // Altura deseada para la vista previa
        val ratio = page.height.toFloat() / page.width.toFloat()
        val targetWidth = (targetHeight.value / ratio).toInt()

        val bitmap = Bitmap.createBitmap(
            targetWidth,
            targetHeight.value.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val renderQuality = PdfRenderer.Page.RENDER_MODE_FOR_PRINT
        val renderRect = Rect(0, 0, targetWidth, targetHeight.value.toInt())
        page.render(bitmap, renderRect, null, renderQuality)

        val imageBitmap = bitmap.asImageBitmap()

        Card(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick() },
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
                    modifier = Modifier.size(targetWidth.dp, targetHeight),
                    contentScale = ContentScale.FillWidth // Escala para ajustar el ancho de la imagen
                )
                Text(
                    text = "Archivo PDF",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
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


