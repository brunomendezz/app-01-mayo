package com.mayorista.oscar.mayoristaoscar.domain

import android.graphics.pdf.PdfDocument
import com.mayorista.oscar.mayoristaoscar.data.PdfRepository
import javax.inject.Inject

class GetNewPdf @Inject constructor(var repo: PdfRepository) {

    suspend fun getPdf(): ByteArray? {
        return repo.getPdf()
    }
}