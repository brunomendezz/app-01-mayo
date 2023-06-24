package com.mayorista.oscar.mayoristaoscar.data.repos

import com.google.android.gms.maps.model.LatLng
import com.mayorista.oscar.mayoristaoscar.data.model.Marcador
import javax.inject.Inject

class MarcadorRepository @Inject constructor(){

    companion object UbicacionesDataProvider {
        var ubicaciones = mutableListOf(
            Marcador("TAPIALES", LatLng(-34.70054831905432, -58.50331634388686)),
            Marcador("PONTEVEDRA", LatLng(-34.74277906543054, -58.693338946004005)),
        )
    }
    fun getUbicacionesRapidas(): MutableList<Marcador> {
        return ubicaciones.toMutableList()
    }

    fun borrarUbicacion(ubicacion: Marcador){
        ubicaciones.remove(ubicacion)
    }

    fun agregarUbicacion(ubicacion:List<Marcador>){
        ubicaciones.addAll(ubicacion)
    }
}