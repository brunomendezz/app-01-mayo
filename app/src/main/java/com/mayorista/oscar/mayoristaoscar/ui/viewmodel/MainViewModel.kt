package com.mayorista.oscar.mayoristaoscar.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayorista.oscar.mayoristaoscar.data.model.Marcador
import com.mayorista.oscar.mayoristaoscar.data.model.ProductosEnOferta
import com.mayorista.oscar.mayoristaoscar.data.repos.MarcadorRepository
import com.mayorista.oscar.mayoristaoscar.domain.PdfServices
import com.mayorista.oscar.mayoristaoscar.domain.ProductoServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val marcadorRepository: MarcadorRepository,
    private val pdfServices: PdfServices,
    private val productoServices: ProductoServices

    ):ViewModel() {



    private val _pdfDocument =MutableLiveData<ByteArray?>()
    val pdfDocument : LiveData<ByteArray?> = _pdfDocument

    private val _vistaPreviaPDF =MutableLiveData<ImageBitmap?>()
    val vistaPreviaPDF : LiveData<ImageBitmap?> = _vistaPreviaPDF

    private val _productosEnOferta =MutableLiveData<ProductosEnOferta?>()
    val productosEnOferta : LiveData<ProductosEnOferta?> = _productosEnOferta

    private val _sucursales = MutableLiveData(emptyList<Marcador>())


    var screenUbication by mutableStateOf("home_screenn")

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa

    private val _showDialogPrecio = MutableLiveData<Boolean>()
    val showDialogPrecio: LiveData<Boolean> = _showDialogPrecio

    init {
        viewModelScope.launch {
            _sucursales.value = marcadorRepository.getSucursales()
            _pdfDocument.value = pdfServices.getPdf()?.bytes
            _vistaPreviaPDF.value = obtenerVistaPreviaPDF()
           // _productosEnOferta.value = productoServices.getProductosEnOferta()
        }
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: Marcador) {
        _ubicacionMapa.value = ubicacion
    }

    fun obtenerVistaPreviaPDF(): ImageBitmap? {
        val pdfByteArray = _pdfDocument.value
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
            val renderQuality = PdfRenderer.Page.RENDER_MODE_FOR_PRINT // Calidad del render
            val renderRect = Rect(0, 0, targetWidth, targetHeight.value.toInt())
            page.render(bitmap, renderRect, null, renderQuality)

            val imageBitmap = bitmap.asImageBitmap()

            page.close()
            renderer.close()
            parcelFileDescriptor.close()
            file.delete()

            return imageBitmap
        }

        return null
    }

    fun showDialog() {
        _showDialogPrecio.value = true
    }
    fun ondismisDialog(){
        _showDialogPrecio.value=false
    }

}