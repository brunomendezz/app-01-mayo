package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel

interface ProductosRepository {
    suspend fun getProductosEnOferta(): List<ProductoModel>?
    suspend fun getInfoProducto(codeBarcode: String):ProductoModel?
    suspend fun getPrecioProducto(codInterno: String):String
}