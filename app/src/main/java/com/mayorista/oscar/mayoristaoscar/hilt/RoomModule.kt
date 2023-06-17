package com.mayorista.oscar.mayoristaoscar.hilt

import android.content.Context
import androidx.room.Room
import com.mayorista.oscar.mayoristaoscar.data.dba.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val APP_DATABASE_NAME = "mayo_oscar_dba"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context : Context)=
        Room.databaseBuilder(context,AppDatabase::class.java,APP_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(db:AppDatabase)=db.userDao()
}