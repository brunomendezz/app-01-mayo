package com.mayorista.oscar.mayoristaoscar.hilt

import com.mayorista.oscar.mayoristaoscar.data.PdfRepository
import com.mayorista.oscar.mayoristaoscar.data.repos.PdfCloudStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainModule {
    @Binds
    abstract fun bindPdfRepositry(kittiesRestRepo: PdfCloudStorageRepository): PdfRepository
}