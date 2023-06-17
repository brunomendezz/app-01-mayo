package com.mayorista.oscar.mayoristaoscar.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebase(): com.google.firebase.ktx.Firebase {
        return com.google.firebase.ktx.Firebase
    }
}