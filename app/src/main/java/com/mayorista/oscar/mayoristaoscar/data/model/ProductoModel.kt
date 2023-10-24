package com.mayorista.oscar.mayoristaoscar.data.model

import com.google.gson.annotations.SerializedName

data class ProductoModel(
    @SerializedName ("cod_articu")
    var cod_articu:String,
    @SerializedName ("descripcio")
    var descripcio: String,
    @SerializedName("precio")
    var precio:String = "0",
    @SerializedName("ventas")
    var ventas : Array<ProductoModel> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductoModel

        if (cod_articu != other.cod_articu) return false
        if (descripcio != other.descripcio) return false
        if (precio != other.precio) return false
        if (!ventas.contentEquals(other.ventas)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cod_articu.hashCode()
        result = 31 * result + descripcio.hashCode()
        result = 31 * result + precio.hashCode()
        result = 31 * result + ventas.contentHashCode()
        return result
    }
}