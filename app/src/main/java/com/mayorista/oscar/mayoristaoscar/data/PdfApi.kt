package com.mayorista.oscar.mayoristaoscar.data

import android.graphics.pdf.PdfDocument
import retrofit2.Response
import retrofit2.http.GET

interface PdfApi {

    @GET("/v1/images/search?limit=3")
    suspend fun getPdf(): Response<PdfDocument?>
}