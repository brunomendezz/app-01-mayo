package com.mayorista.oscar.mayoristaoscar.data.repos

import android.graphics.pdf.PdfDocument
import com.mayorista.oscar.mayoristaoscar.data.PdfRepository

class PdfDbaRepository: PdfRepository {
    override suspend fun getPdf(): ByteArray? {
        TODO("Not yet implemented")
    }
}