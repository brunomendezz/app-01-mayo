package com.mayorista.oscar.mayoristaoscar.data.dba

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayorista.oscar.mayoristaoscar.data.model.PdfModelMayo
import javax.inject.Inject

@Database(entities = [PdfModelMayo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}