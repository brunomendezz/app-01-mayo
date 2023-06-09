package com.mayorista.oscar.mayoristaoscar.data

import android.graphics.pdf.PdfDocument

interface PdfRepository {
    suspend fun getPdf():PdfDocument?
}