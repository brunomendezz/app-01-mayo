package com.mayorista.oscar.mayoristaoscar.data.repos
<<<1<<1
import com.google.mlkit.vision.barcode.common.Barcode
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class ProductosRestRepositoryo @Inject constructor(builder: Builder) : ProductosRepository {

    var retrofit: Retrofit = builder
        .baseUrl("https://api.mayoejemplo.com")
        .build()

    override suspend fun getProductosEnOferta(): List<ProductoModel>? {
        val productoApi = retrofit.create(ProductosApi::class.java)
        val call = productoApi.getProductosEnOferta()
        val productos = call.body()

        if (call.isSuccessful) {
            return productos
            //HAREMOS ALGO AQUI
        }

        return emptyList()
    }

    override suspend fun getInfoProducto(codeBarcode: Barcode): ProductoModel? {
        val productoApi = retrofit.create(ProductosApi::class.java)
        val call = productoApi.getInfoProducto(codArticulo = codeBarcode.toString(), perfil = "Bruno")
        val producto = call.body()

        if (call.isSuccessful) {
            return producto
        }

        return null
    }
}