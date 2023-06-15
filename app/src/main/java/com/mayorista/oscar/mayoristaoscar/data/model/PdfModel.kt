package com.mayorista.oscar.mayoristaoscar.data.model

import com.google.android.gms.tasks.Task


data class PdfModel(val bytes: Task<ByteArray>)
