package com.mayorista.oscar.mayoristaoscar.data.repos

import android.util.Log
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import okio.IOException
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class ProductosRestRepository @Inject constructor(builder: Builder) : ProductosRepository {

    var retrofit: Retrofit = builder.build()

    override suspend fun getProductosEnOferta(): List<ProductoModel>? {
        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val call = productoApi.getProductosEnOferta()

            if (call.isSuccessful) {
                return call.body()
            } else {
                // Manejar la respuesta no exitosa aquí
                Log.e("ProductosRestRepositoryo", "Respuesta no exitosa: ${call.code()}")
            }
        } catch (e: java.net.SocketTimeoutException) {
            // Manejar la excepción de tiempo de espera aquí
            Log.e("ProductosRestRepositoryo", "Timeout: ${e.message}")
        } catch (e: Exception) {
            // Manejar otras excepciones generales aquí
            Log.e("ProductosRestRepositoryo", "Excepción general: ${e.message}")
        }
        return emptyList()
    }


    override suspend fun getInfoProducto(codeBarcode: String): ProductoModel {

        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val response = productoApi.getInfoProducto(codeBarcode)

            if (response.isSuccessful) {
                val producto = response.body()?.get(0)
                if (producto != null) {
                    val precio = getPrecioProducto(producto.cod_articu)
                    val precioDouble = precio.toDoubleOrNull()

                    if (precioDouble != null) {
                        // Formatear el precio a dos decimales
                        val precioFormateado = String.format("%.2f", precioDouble)
                        return ProductoModel(
                            producto.cod_articu,
                            producto.descripcio,
                            precioFormateado
                        )
                    }else{
                        return ProductoModel(
                            producto.cod_articu,
                            producto.descripcio,
                            precio
                        )
                    }

                } else {
                    Log.e("ProductosRestRepository", "El cuerpo de la respuesta es nulo.")
                }
            } else {
                Log.e(
                    "ProductosRestRepository",
                    "La solicitud no fue exitosa. Código HTTP: ${response.code()}"
                )
            }
        } catch (e: IOException) {
            Log.e("ProductosRestRepository", "Excepción de IO: ${e.message}")
        } catch (e: Exception) {
            Log.e("ProductosRestRepository", "Excepción general: ${e.message}")
        }

        return ProductoModel("Código interno no disponible.", "Descripción no disponible.")
    }


    override suspend fun getPrecioProducto(codInterno: String): String {
        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val call = productoApi.getPrecioProducto(codArticulo = codInterno, perfil = "PARCIAL")

            if (call.isSuccessful) {
                val body = call.body()
                if (body != null) {
                    val precio = body[0].ventas[0].precio

                    return precio
                } else {
                    Log.e("ProductosRestRepository", "El precio no está disponible.")
                }
            } else {
                Log.e(
                    "ProductosRestRepository",
                    "La solicitud no fue exitosa. Código HTTP: ${call.code()}"
                )
            }
        } catch (e: IOException) {
            Log.e("ProductosRestRepository", "Excepción de IO: ${e.message}")
        } catch (e: Exception) {
            Log.e("ProductosRestRepository", "Excepción general: ${e.message}")
        }
        return "El precio no está disponible."
    }
}
