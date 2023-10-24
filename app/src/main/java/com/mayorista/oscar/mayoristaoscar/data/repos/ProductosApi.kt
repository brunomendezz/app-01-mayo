package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.ProductoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductosApi {
    @GET("/products?limit=150")
    suspend fun getProductosEnOferta(): Response<List<ProductoModel>>

    @GET("/api/articulos/{cod_articulo}")
    suspend fun getInfoProducto(
        @Path("cod_articulo") codArticulo: String
    ): Response<Array<ProductoModel>>


    @GET("/api/articulo-precio/{cod_articu}/perfil/{cod_perfil}")
    suspend fun getPrecioProducto(
        @Path("cod_articu") codArticulo: String,
        @Path("cod_perfil") perfil: String
    ): Response<Array<ProductoModel>>
}