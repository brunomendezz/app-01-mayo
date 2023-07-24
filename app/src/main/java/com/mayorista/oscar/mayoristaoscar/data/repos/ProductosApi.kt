package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductosApi {
    @GET("urlDelMAyo")
    suspend fun getProductosEnOferta(): Response<List<ProductoModel>>

    @GET("/api/articulo-precio/{cod_articu}/perfil/{perfil}")
    suspend fun getInfoProducto(
        @Path("cod_articu") codArticulo: String,
        @Path("perfil") perfil: String
    ): Response<ProductoModel>
}