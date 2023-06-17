package com.mayorista.oscar.mayoristaoscar.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pdf")
data class PdfModelMayo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id:Int= 0,
    @ColumnInfo(name = "pdfBytes") val bytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PdfModelMayo

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
