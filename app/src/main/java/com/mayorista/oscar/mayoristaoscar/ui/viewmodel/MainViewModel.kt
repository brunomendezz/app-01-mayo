package com.mayorista.oscar.mayoristaoscar.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayorista.oscar.mayoristaoscar.data.model.Marcador
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.ProductosEnOferta
import com.mayorista.oscar.mayoristaoscar.data.repos.MarcadorRepository
import com.mayorista.oscar.mayoristaoscar.domain.PdfServices
import com.mayorista.oscar.mayoristaoscar.domain.ProductoServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val marcadorRepository: MarcadorRepository,
    private val pdfServices: PdfServices,
    private val productoServices: ProductoServices

    ):ViewModel() {

    private val _pdfDocument =MutableLiveData<ByteArray?>()
    val pdfDocument : LiveData<ByteArray?> = _pdfDocument

    private val _productosEnOferta =MutableLiveData<ProductosEnOferta?>()
    val productosEnOferta : LiveData<ProductosEnOferta?> = _productosEnOferta

    private val _ubicacionesRapidas = MutableLiveData(emptyList<Marcador>())


    var screenUbication by mutableStateOf("home_screenn")

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa


    init {
        viewModelScope.launch {
            _ubicacionesRapidas.value = marcadorRepository.getUbicacionesRapidas()
            _pdfDocument.value = pdfServices.getPdf().bytes
           // _productosEnOferta.value = productoServices.getProductosEnOferta()
        }
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: Marcador) {
        _ubicacionMapa.value = ubicacion
    }
}