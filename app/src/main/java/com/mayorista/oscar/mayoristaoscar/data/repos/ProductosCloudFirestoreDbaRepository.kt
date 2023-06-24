package com.mayorista.oscar.mayoristaoscar.data.repos

import com.google.firebase.ktx.Firebase
import com.mayorista.oscar.mayoristaoscar.data.model.ProductosEnOferta
import javax.inject.Inject

class ProductosCloudFirestoreDbaRepository@Inject constructor(private val firebase : Firebase): ProductosRepository {
    override suspend fun getProductosEnOferta(): ProductosEnOferta? {
        TODO("Not yet implemented")
    }
}