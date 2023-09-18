package com.mayorista.oscar.mayoristaoscar.data.repos

import android.util.Log
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import okio.IOException
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class ProductosRestRepositoryo @Inject constructor(builder: Builder) : ProductosRepository {

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


    override suspend fun getInfoProducto(codeBarcode: String): ProductoModel? {
     //   val token = "30|wVS3NbIrLpWCPEvmBzp03dczSs90R4LUkC9ME23T"

        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val request = productoApi.getInfoProducto(
                codArticulo = 7791290792401
            )

            if (request.isSuccessful) {
                val responseBody = request.body()
                    val codigoInterno = responseBody?.cod_articu!!
                    val descripcion = responseBody.descripcio!!

                    val producto = ProductoModel(codigoInterno,descripcion)
                    return producto
            } else {
                Log.e(
                    "ProductosRestRepository",
                    "La solicitud no fue exitosa. Código HTTP: ${request.code()}"
                )
            }
        } catch (e: IOException) {
            Log.e("ProductosRestRepository", "Excepción de IO: ${e.message}")
        } catch (e: Exception) {
            Log.e("ProductosRestRepository", "Excepción general: ${e.message}")
        }
        return ProductoModel("descripcion no disponible", "descripcion no disponible")
    }


    override suspend fun getPrecioProducto(codInterno: String): String {
        val token = "30|wVS3NbIrLpWCPEvmBzp03dczSs90R4LUkC9ME23T"
        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val call = productoApi.getPrecioProducto(codArticulo = codInterno, perfil = "PARCIAL")

            if (call.isSuccessful) {
                val body = call.body()
                if (body != null) {
                    return body.ventas[0].precio
                } else {
                    Log.e("ProductosRestRepository", "El precio del producto no está disponible")
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

        // En caso de error o excepción, regresa un valor predeterminado, como 0.0.
        return "El precio del producto no esta disponible"
    }



  /*  suspend fun iniciarSesion(): RespuestaInicioSesion {
        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val call = productoApi.loginApi(Credenciales("supervisor", "123456"))

            Log.i("ApiRest", "Código de respuesta: ${call.code()}")
            Log.i("ApiRest", "Cuerpo de respuesta: ${call.body()}")
            Log.i("ApiRest", "Encabezados de respuesta: ${call.headers()}")

            if (call.isSuccessful) {
                if (call.body()!=null) {
                    return RespuestaInicioSesion(call.body()!!.token)
                } else {
                    Log.e("ProductosRestRepository", "La respuesta del servidor está vacía")
                }
            } else {
                Log.e(
                    "ProductosRestRepository",
                    "Inicio de sesión fallido. Código de error: ${call.code()},"
                )
            }
        } catch (e: IOException) {
            Log.e("ProductosRestRepository", "Excepción de IO: ${e.message}")
        } catch (e: Exception) {
            Log.e("ProductosRestRepository", "Excepción general: ${e.message}")
        }

        // En caso de error o excepción, puedes devolver un objeto JSONObject vacío.
        return RespuestaInicioSesion("no devolvio token")
    }*/

  /*  suspend fun loginEmpresa(): String {
        val token = "24|MLW0kGTuexpBME8znam9XjTqzL8CAueWGUBM1aMc"
        val empresa = 2
        try {
            val productoApi = retrofit.create(ProductosApi::class.java)
            val call = productoApi.loginApiEmpresa(token, empresa)
            Log.i("ApiRest", "Código de respuesta: ${call.code()}")
            Log.i("ApiRest", "Cuerpo de respuesta: ${call.body()}")
            Log.i("ApiRest", "Encabezados de respuesta: ${call.headers()}")

            if (call.isSuccessful) {
                val responseBody = call.body()?.string()
                if (responseBody != null) {
                    val jsonObject = JSONObject(responseBody)
                    return jsonObject.toString(4)
                } else {
                    Log.e("ApiRest", "La respuesta del servidor está vacía")
                }
            } else {
                Log.e("ApiRest", "Logeo de empresa fallido. Código de error: ${call.code()}")
            }
        } catch (e: IOException) {
            Log.e("ApiRest", "Excepción de IO: ${e.message}")
        } catch (e: Exception) {
            Log.e("ApiRest", "Excepción general: ${e.message}")
        }

        // En caso de error o excepción, puedes devolver una cadena de texto vacía o un mensaje de error.
        return "fallo el login empresa"
    }*/
}

