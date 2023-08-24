package com.mayorista.oscar.mayoristaoscar.domain

import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.repos.ProductosRestRepositoryo
import javax.inject.Inject

class ProductoServices @Inject constructor(var productoRepo : ProductosRestRepositoryo)  {
    suspend fun getProductosEnOferta(): List<ProductoModel>? {
        return productoRepo.getProductosEnOferta()
    }

    suspend fun iniciarSesion(){
        productoRepo.iniciarSesion()
    }
}