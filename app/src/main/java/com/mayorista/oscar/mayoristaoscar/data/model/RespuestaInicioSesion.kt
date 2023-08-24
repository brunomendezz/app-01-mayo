package com.mayorista.oscar.mayoristaoscar.data.model

data class RespuestaInicioSesion(val token: String,
                                 val empresas: List<Empresa>)