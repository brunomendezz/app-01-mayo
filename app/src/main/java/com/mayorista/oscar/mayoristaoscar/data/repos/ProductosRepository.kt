package com.mayorista.oscar.mayoristaoscar.data.repos

import com.google.mlkit.vision.barcode.common.Barcode
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel

interface ProductosRepository {
    suspend fun getProductosEnOferta(): List<ProductoModel>?
    suspend fun getInfoProducto(codeBarcode: Barcode): ProductoModel?
}