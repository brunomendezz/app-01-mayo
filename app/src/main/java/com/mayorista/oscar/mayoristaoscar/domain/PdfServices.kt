package com.mayorista.oscar.mayoristaoscar.domain

import android.util.Log
import com.mayorista.oscar.mayoristaoscar.data.model.PdfModelMayo
import com.mayorista.oscar.mayoristaoscar.data.repos.PdfCloudStorageRepository
import com.mayorista.oscar.mayoristaoscar.data.repos.PdfDbaRepository
import com.mayorista.oscar.mayoristaoscar.data.repos.PdfRepository
import dagger.Module
import javax.inject.Inject
import kotlin.math.log

class PdfServices @Inject constructor(var repoC: PdfCloudStorageRepository, var repoDba: PdfDbaRepository) {

    suspend fun getPdf(): PdfModelMayo {
        Log.i("bruno", "getPdf: entre al get")

        val pdfFromDb = repoDba.getPdf()
        Log.i("bruno", "getPdf: pedi en dba")


        return if (pdfFromDb == null) {
            Log.i("bruno", "getPdf: dba estaba vacia")
            val pdfFromCloud = repoC.getPdf()
            Log.i("bruno", "getPdf: pedi el cloud")
            if (pdfFromCloud != null) {
                repoDba.savePdf(pdfFromCloud)
                Log.i("bruno", "getPdf: guarde en dba")
            }
            Log.i("bruno", "getPdf: retorno result de cloud")
            pdfFromCloud
        } else {
            Log.i("bruno", "getPdf: retorno dba")
            pdfFromDb
        }
    }
}