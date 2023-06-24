package com.mayorista.oscar.mayoristaoscar.domain

import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.ProductosEnOferta
import com.mayorista.oscar.mayoristaoscar.data.repos.ProductosCloudFirestoreDbaRepository
import javax.inject.Inject

class ProductoServices @Inject constructor(var productoRepo : ProductosCloudFirestoreDbaRepository)  {
    suspend fun getProductosEnOferta(): ProductosEnOferta? {
        return productoRepo.getProductosEnOferta()
    }
}