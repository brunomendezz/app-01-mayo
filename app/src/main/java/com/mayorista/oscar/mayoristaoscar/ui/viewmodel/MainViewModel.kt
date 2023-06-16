package com.mayorista.oscar.mayoristaoscar.ui.viewmodel

import android.graphics.pdf.PdfDocument
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayorista.oscar.mayoristaoscar.data.Marcador
import com.mayorista.oscar.mayoristaoscar.data.MarcadorRepository
import com.mayorista.oscar.mayoristaoscar.data.repos.PdfCloudStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel:ViewModel() {
    val mardorRepository = MarcadorRepository()
    val pdfCloudStorageRepo = PdfCloudStorageRepository()
    private val _pdfDocument =MutableLiveData<ByteArray?>()
    val pdfDocument : LiveData<ByteArray?> = _pdfDocument

    private val _ubicacionesRapidas = MutableLiveData(emptyList<Marcador>())


    var screenUbication by mutableStateOf("home_screenn")

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa


    init {
        viewModelScope.launch {
            _ubicacionesRapidas.value = mardorRepository.getUbicacionesRapidas()
            _pdfDocument.value = pdfCloudStorageRepo.getPdf()
        }
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: Marcador) {
        _ubicacionMapa.value = ubicacion
    }
}