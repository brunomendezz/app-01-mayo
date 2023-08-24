package com.mayorista.oscar.mayoristaoscar.data.repos

import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.mayorista.oscar.mayoristaoscar.data.model.Credenciales
import com.mayorista.oscar.mayoristaoscar.data.model.Empresa
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class ProductosRestRepositoryo @Inject constructor(builder: Builder) : ProductosRepository {

    var retrofit: Retrofit = builder
        .baseUrl("https://fakestoreapi.com")
        .build()

    private val credenciales = Credenciales("PEPITO", "1234")


    override suspend fun getProductosEnOferta(): List<ProductoModel>? {
        val productoApi = retrofit.create(ProductosApi::class.java)
        val call = productoApi.getProductosEnOferta()
        val productos = call.body()

        if (call.isSuccessful) {
            return productos
        }
        return emptyList()
    }

    override suspend fun getInfoProducto(codeBarcode: Barcode): ProductoModel? {
        val productoApi = retrofit.create(ProductosApi::class.java)
        val call =
            productoApi.getInfoProducto(codArticulo = codeBarcode.toString(), perfil = "Bruno")
        val producto = call.body()

        if (call.isSuccessful) {
            return producto
        }

        return null
    }

    suspend fun iniciarSesion() {

        val productoApi = retrofit.create(ProductosApi::class.java)
        val call = productoApi.loginApi(credenciales)
        Log.i("ApiRest", "Código de respuesta: ${call.code()}")
        Log.i("ApiRest", "Cuerpo de respuesta: ${call.body()}")
        Log.i("ApiRest", "Encabezados de respuesta: ${call.headers()}")


        if (call.isSuccessful) {
            val token = call.body()?.token
            if (token != null) {
                Log.i("ApiRest", "Inicio de sesión exitoso. Token: $token")

            }
        } else {
            Log.i("ApiRest", "Inicio de sesión fallido. Código de error: ${call.code()}")
        }
    }

    suspend fun loginEmpresa(token: String, empresa: Empresa): Boolean {
        val productoApi = retrofit.create(ProductosApi::class.java)
        val call = productoApi.loginApiEmpresa(token, empresa)
        Log.i("ApiRest", "Código de respuesta: ${call.code()}")
        Log.i("ApiRest", "Cuerpo de respuesta: ${call.body()}")
        Log.i("ApiRest", "Encabezados de respuesta: ${call.headers()}")

        if (call.isSuccessful) {

            Log.i("ApiRest", "Logeo de empresa exitoso. Token: $token")

            return true
        }
        return false
    }

}
