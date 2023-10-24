package com.mayorista.oscar.mayoristaoscar.domain

import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.repos.ProductosRestRepository
import javax.inject.Inject

class ProductoServices @Inject constructor(private val productoRepo : ProductosRestRepository) {
    suspend fun getProductosEnOferta(): List<ProductoModel>? {
        return productoRepo.getProductosEnOferta()
    }

    suspend fun getInfoProducto(barcode: String): ProductoModel {
        return productoRepo.getInfoProducto(barcode)
    }
}