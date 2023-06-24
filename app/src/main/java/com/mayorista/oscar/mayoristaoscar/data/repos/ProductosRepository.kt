package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.ProductosEnOferta

interface ProductosRepository {
    suspend fun getProductosEnOferta():ProductosEnOferta?
}