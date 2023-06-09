package com.mayorista.oscar.mayoristaoscar.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayorista.oscar.mayoristaoscar.data.Marcador
import com.mayorista.oscar.mayoristaoscar.data.MarcadorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    val mardorRepository = MarcadorRepository()
    private val _ubicacionesRapidas = MutableLiveData(emptyList<Marcador>())

    var screenUbication by mutableStateOf("home_screenn")

    private val _ubicacionMapa = MutableLiveData<Marcador>()
    val ubicacionMapa: LiveData<Marcador> = _ubicacionMapa

    init {
        viewModelScope.launch {
            _ubicacionesRapidas.value = mardorRepository.getUbicacionesRapidas()
        }
    }

    fun nuevaUbicacionSeleccionadaEnMapa(ubicacion: Marcador) {
        _ubicacionMapa.value = ubicacion
    }
}