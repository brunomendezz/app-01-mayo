package com.mayorista.oscar.mayoristaoscar.domain

import com.google.mlkit.vision.barcode.common.Barcode
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.RespuestaInicioSesion
import com.mayorista.oscar.mayoristaoscar.data.repos.ProductosRestRepositoryo
import javax.inject.Inject

class ProductoServices @Inject constructor(private val productoRepo : ProductosRestRepositoryo) {
    suspend fun getProductosEnOferta(): List<ProductoModel>? {
        return productoRepo.getProductosEnOferta()
    }

    suspend fun getInfoProducto(barcode: String): ProductoModel? {
        return productoRepo.getInfoProducto(barcode)
    }

  /*  suspend fun iniciarSesion():RespuestaInicioSesion{
        return productoRepo.iniciarSesion()
    }*/

  /*  suspend fun loginEmpresa():String{
        return productoRepo.loginEmpresa()
    }*/
}