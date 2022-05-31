package com.lalitj.mvvmpractice.network

import com.lalitj.mvvmpractice.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit? {
        val client = OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Ocp-Apim-Subscription-Key", "223f900e901a4a61baedc803eef5153b")
                        .addHeader("sessionId", "2q4MX9F4C824F0B05640EBA17C0925840AF53/9194171/9d4493e716153642/0")
                        .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            /*.connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)*/
            .build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://prodapitm.bajajfinserv.in/ServiceAPPWS_Session/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}