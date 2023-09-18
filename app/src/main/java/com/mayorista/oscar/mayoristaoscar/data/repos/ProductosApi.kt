package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.Credenciales
import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import com.mayorista.oscar.mayoristaoscar.data.model.RespuestaInicioSesion
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductosApi {
    @GET("/products?limit=150")
    suspend fun getProductosEnOferta(): Response<List<ProductoModel>>

    @GET("/api/articulos/")
    suspend fun getInfoProducto(
        @Path("cod_articu") codArticulo: Long
    ): Response<ProductoModel>

    @GET("/api/articulo-precio/{cod_articu}/perfil/{cod_perfil?}")
    suspend fun getPrecioProducto(
        @Path("cod_articu") codArticulo: String,
        @Path("cod_perfil") perfil: String
    ): Response<ProductoModel>



    @POST("/api/login")
    suspend fun loginApi(@Body crendencial: Credenciales):Response<RespuestaInicioSesion>

    @POST("/api/empresa")
    suspend fun loginApiEmpresa( @Header("Authorization") token: String
                                 ,@Body IDEmpresa : Int):Response<ResponseBody>
}