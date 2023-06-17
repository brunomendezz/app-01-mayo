package com.mayorista.oscar.mayoristaoscar.data.repos

import com.mayorista.oscar.mayoristaoscar.data.model.PdfModelMayo

interface PdfRepository {
    suspend fun getPdf():PdfModelMayo?
}