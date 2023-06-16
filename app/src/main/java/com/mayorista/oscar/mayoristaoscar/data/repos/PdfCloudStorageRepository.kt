package com.mayorista.oscar.mayoristaoscar.data.repos

import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mayorista.oscar.mayoristaoscar.data.PdfRepository
import kotlinx.coroutines.CompletableDeferred
import java.io.ByteArrayInputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PdfCloudStorageRepository : PdfRepository {

    override suspend fun getPdf(): ByteArray? {
        val storage = Firebase.storage
        val gsReference =
            storage.getReferenceFromUrl("gs://mayorista-oscar.appspot.com/Listas/1_Lista_Precios_09-06-2023.PDF")

        val deferred = CompletableDeferred<ByteArray?>()

        gsReference.getBytes(Long.MAX_VALUE)
            .addOnSuccessListener { bytes ->
                Log.i("bruno", "getPdf: la llamada fue exitosa")
                deferred.complete(bytes)
            }
            .addOnFailureListener { exception ->
                Log.i("bruno", "getPdf: la llamada falló", exception)
                deferred.complete(null)
            }

        return deferred.await()
    }
}


