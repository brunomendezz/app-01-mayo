package com.mayorista.oscar.mayoristaoscar.hilt

import com.mayorista.oscar.mayoristaoscar.data.repos.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(ViewModelComponent::class)
class HttpClientModule {

    @Provides
    fun retrofitBuilder(): Builder {

       val token = "30|wVS3NbIrLpWCPEvmBzp03dczSs9OR4LUkC9ME23T"
        return Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(token))
                .connectTimeout(10, TimeUnit.SECONDS) // Tiempo de espera para la conexión
                .readTimeout(10, TimeUnit.SECONDS).build())
            .baseUrl("http://192.168.2.51:8000")
            .addConverterFactory(GsonConverterFactory.create())
    }


    private fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier { _, _ -> true }
        return this
    }
}
