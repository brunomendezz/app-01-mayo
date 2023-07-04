package com.mayorista.oscar.mayoristaoscar.data.repos

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mayorista.oscar.mayoristaoscar.data.model.PdfModelMayo
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class PdfCloudStorageRepository @Inject constructor(var firebase : Firebase): PdfRepository {

    override suspend fun getPdf(): PdfModelMayo? {
        val gsReference =
            firebase.storage.getReferenceFromUrl("gs://mayorista-oscar-4a2db.appspot.com/listas/lista.PDF")

        val deferred = CompletableDeferred<PdfModelMayo?>()

        gsReference.getBytes(Long.MAX_VALUE)
            .addOnSuccessListener { bytes ->
                Log.i("bruno", "getPdf: la llamada fue exitosa, la busque en cloud")
                deferred.complete(PdfModelMayo(0,bytes))
            }
            .addOnFailureListener { exception ->
                Log.i("bruno", "getPdf: la llamada falló", exception)
                deferred.complete(null) }

        return deferred.await()
    }

}


