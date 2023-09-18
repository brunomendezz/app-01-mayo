package com.mayorista.oscar.mayoristaoscar.data.repos

import com.google.mlkit.vision.barcode.common.Barcode
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.VentasModel
import retrofit2.Response

interface ProductosRepository {
    suspend fun getProductosEnOferta(): List<ProductoModel>?
    suspend fun getInfoProducto(codeBarcode: String):ProductoModel?
    suspend fun getPrecioProducto(codInterno: String):String
}