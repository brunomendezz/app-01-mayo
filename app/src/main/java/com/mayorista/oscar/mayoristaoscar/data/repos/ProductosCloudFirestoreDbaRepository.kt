package com.mayorista.oscar.mayoristaoscar.data.repos

import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.common.Barcode
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.ProductosEnOferta
import javax.inject.Inject

class ProductosCloudFirestoreDbaRepository@Inject constructor(private val firebase : Firebase): ProductosRepository {
    override suspend fun getProductosEnOferta(): List<ProductoModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun getInfoProducto(codeBarcode: Barcode): ProductoModel? {
        TODO("Not yet implemented")
    }
}