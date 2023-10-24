package com.mayorista.oscar.mayoristaoscar.data.dba

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mayorista.oscar.mayoristaoscar.data.model.PdfModelMayo

@Dao
interface UserDAO {
    @Query("SELECT * FROM pdf")
    fun getPdf(): PdfModelMayo?

    @Insert
    suspend fun savePdf(pdf: PdfModelMayo)

    @Query("DELETE FROM pdf")
    suspend fun deletePdf()

}