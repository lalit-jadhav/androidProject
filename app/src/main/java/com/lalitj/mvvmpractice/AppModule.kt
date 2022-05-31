package com.lalitj.mvvmpractice

import android.app.Application
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lalitj.mvvmpractice.network.APIInterface
import com.lalitj.mvvmpractice.network.AppAPIHelper
import com.lalitj.mvvmpractice.network.CoroutineAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /*private var mApplication: Application? = null

    @Provides
    @Singleton
    fun provideApplication(): Application? {
        return mApplication
    }*/

    @Provides
    @Singleton
    fun getApiHelper(apiService: APIInterface): CoroutineAPIService = AppAPIHelper(apiService)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIInterface = createService(retrofit)

    private inline fun <reified T> createService(retrofit: Retrofit): T =
        retrofit.create(T::class.java)

    @Provides
    fun provideCoroutineRetrofit(ok: OkHttpClient, gson: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://prodapitm.bajajfinserv.in/ServiceAPPWS_Session/")
            .client(ok)
            .addConverterFactory(gson)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Ocp-Apim-Subscription-Key", "223f900e901a4a61baedc803eef5153b")
                        .addHeader("sessionId", "2T4yOC096EB2D0977BA665429B926FA3BB8EE/9194171/ca4d316881a242e1/0")
                        .build()
                chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
//            .sslSocketFactory(sslSocketFactory, array[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .callTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideLoggingInt(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.NONE else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setLenient().setPrettyPrinting().create())
}