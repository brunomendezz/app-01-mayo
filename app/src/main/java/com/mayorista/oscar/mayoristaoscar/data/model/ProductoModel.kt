package com.mayorista.oscar.mayoristaoscar.data.model

import com.google.gson.annotations.SerializedName

data class ProductoModel(
    @SerializedName ("cod_articu")
    var cod_articu:String,
    @SerializedName ("descrpcio")
    var descripcio: String,
    @SerializedName ("ventas")
    var ventas: Array<VentasModel> = emptyArray(),
    var precio:String = "0"
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductoModel

        if (cod_articu != other.cod_articu) return false
        if (descripcio != other.descripcio) return false
        if (!ventas.contentEquals(other.ventas)) return false
        if (precio != other.precio) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cod_articu.hashCode()
        result = 31 * result + descripcio.hashCode()
        result = 31 * result + ventas.contentHashCode()
        result = 31 * result + precio.hashCode()
        return result
    }
}