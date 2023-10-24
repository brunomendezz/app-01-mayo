package com.mayorista.oscar.mayoristaoscar.ui.viewmodel

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
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
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.repos.MarcadorRepository
import com.mayorista.oscar.mayoristaoscar.domain.PdfServices
import com.mayorista.oscar.mayoristaoscar.domain.ProductoServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val marcadorRepository: MarcadorRepository,
    private val pdfServices: PdfServices,
    private val productoServices: ProductoServices,
    private val sharedPreferences: SharedPreferences

) : ViewModel() {

    private val _pdfDocument = MutableLiveData<ByteArray?>()
    val pdfDocument: LiveData<ByteArray?> = _pdfDocument

    private val _vistaPreviaPDF = MutableLiveData<ImageBitmap?>()
    val vistaPreviaPDF: LiveData<ImageBitmap?> = _vistaPreviaPDF

    private val _productosEnOferta = MutableLiveData<List<ProductoModel>?>()
    val productosEnOferta: LiveData<List<ProductoModel>?> = _productosEnOferta

    private val _listaActualizando = MutableLiveData(false)
     val listaActualizando: LiveData<Boolean> =_listaActualizando

    private val _ultimaActualizacion = MutableLiveData("N/A")
    val ultimaActualizacion: LiveData<String> =_ultimaActualizacion


    private val _sucursales = MutableLiveData(emptyList<Marcador>())


    var screenUbication by mutableStateOf("home_screenn")

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa

    private val _infoProducto = MutableLiveData<ProductoModel>()
    val infoProducto: LiveData<ProductoModel> = _infoProducto

    private val _infoDeSesion = MutableLiveData<String>()
    val infoDeSesion: LiveData<String> = _infoDeSesion


    private val _showDialogCircularProgres = MutableLiveData<Boolean>()
    val showDialogCircularProgres: LiveData<Boolean> = _showDialogCircularProgres

    private val _dataLoaded = MutableLiveData(false)
    val dataLoaded: LiveData<Boolean> = _dataLoaded


    init {
        viewModelScope.launch {
            _ultimaActualizacion.value=sharedPreferences.getString("ultimaActualizacion","N/A")
            _sucursales.value = marcadorRepository.getSucursales()
            _pdfDocument.value = pdfServices.getPdf()?.bytes
            _vistaPreviaPDF.value = obtenerVistaPreviaPDF()
            _productosEnOferta.value = productoServices.getProductosEnOferta()
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

    fun showDialogProgress() {
        _showDialogCircularProgres.value = true
    }

    fun ondismisDialogProgress() {
        _dataLoaded.value = false
    }

    fun getInfoProducto(barcode: String) {
        viewModelScope.launch {
            try {
                var codigoABuscar = barcode
                if (barcode.startsWith("20")) {
                    codigoABuscar = barcode.substring(0, 7)
                    val pesoStr = barcode.substring(7, 13)
                    val kilos = pesoStr.substring(0, 1).toInt()

                    val gramos = pesoStr.substring(1).toInt()
                    val pesoTotalEnKilos = kilos + (gramos / 10000.0)
                    val infoProductoPesable = productoServices.getInfoProducto(codigoABuscar)
                    val precio = String.format("%.2f", infoProductoPesable.precio.toDouble() * pesoTotalEnKilos)
                    _infoProducto.value = ProductoModel(infoProductoPesable.cod_articu,infoProductoPesable.descripcio,precio)
                }else{
                    val infoProducto = productoServices.getInfoProducto(codigoABuscar)
                    _infoProducto.value = infoProducto
                }

            } catch (e: Exception) {
                // Mane-jar errores si es necesario
            } finally {
                _dataLoaded.value = true
                _showDialogCircularProgres.value = false

            }
        }
    }

    fun actualizarListaDePrecios(){
        viewModelScope.launch {
                _listaActualizando.value = true
           val listaNueva = pdfServices.actualizarLista()?.bytes
            if (listaNueva !=null){
                val fechaHoraActual = Date()
                val formato = SimpleDateFormat("MMM dd HH:mm:ss zzz", Locale.US)
                val fechaHoraFormateada = formato.format(fechaHoraActual)
                 _ultimaActualizacion.value = fechaHoraFormateada
                sharedPreferences.edit().putString("ultimaActualizacion",fechaHoraFormateada).apply()
                 _pdfDocument.value=listaNueva
                _vistaPreviaPDF.value = obtenerVistaPreviaPDF()
            }
            _listaActualizando.value = false
        }

    }
}