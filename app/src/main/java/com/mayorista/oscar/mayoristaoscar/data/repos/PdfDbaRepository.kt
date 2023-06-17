package com.mayorista.oscar.mayoristaoscar.data.repos

import android.util.Log
import com.mayorista.oscar.mayoristaoscar.data.dba.AppDatabase
import com.mayorista.oscar.mayoristaoscar.data.dba.UserDAO
import com.mayorista.oscar.mayoristaoscar.data.model.PdfModelMayo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PdfDbaRepository @Inject constructor(
    private val dba : UserDAO
): PdfRepository {

    override suspend fun getPdf(): PdfModelMayo? {
        return withContext(Dispatchers.IO) {
            dba.getPdf()
        }
    }
    suspend fun savePdf(pdfFromCloud: PdfModelMayo) {
        Log.i("bruno", "savePdf: entre a guardar pdf")
        dba.savePdf(pdfFromCloud)
    }
}