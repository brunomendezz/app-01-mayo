package com.mayorista.oscar.mayoristaoscar.data.model

data class ProductoModel(
    var nombre: String,
    var precio: Double,
    var fechaExpiracion: String,
    var descuento :Int,
    var imagen: String
)