package com.mayorista.oscar.mayoristaoscar.data

import android.graphics.pdf.PdfDocument
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Inject

class PdfRestRepository @Inject constructor(builder: Builder) : PdfRepository {
    var retrofit: Retrofit = builder
        .baseUrl("https://api.thecatapi.com")
        .build()

    override suspend fun getPdf(): PdfDocument? {
        val pdfApi = retrofit.create(PdfApi::class.java)

        val call = pdfApi.getPdf()
        val pdf = call.body()


        if (call.isSuccessful) {
            return pdf!!
        }

return PdfDocument()
    }
}
