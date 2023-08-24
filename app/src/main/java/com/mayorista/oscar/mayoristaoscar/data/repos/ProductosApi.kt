package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.Credenciales
import com.mayorista.oscar.mayoristaoscar.data.model.Empresa
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.RespuestaInicioSesion
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductosApi {
    @GET("/products?limit=150")
    suspend fun getProductosEnOferta(): Response<List<ProductoModel>>

    @GET("/api/articulo-precio/{cod_articu}/perfil/{perfil}")
    suspend fun getInfoProducto(
        @Path("cod_articu") codArticulo: String,
        @Path("perfil") perfil: String
    ): Response<ProductoModel>

    @POST("/api/login")
    suspend fun loginApi(@Body credenciales : Credenciales):Response<RespuestaInicioSesion>

    @POST("/api/empresa")
    suspend fun loginApiEmpresa( @Header("Authorization") token: String
                                 ,@Body empresa : Empresa):Response<RespuestaInicioSesion>
}