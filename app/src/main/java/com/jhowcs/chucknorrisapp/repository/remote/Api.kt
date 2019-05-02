package com.jhowcs.chucknorrisapp.repository.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class Api: BaseApi {
    private val logInterceptor = HttpLoggingInterceptor()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .build()

    val retrofit: Retrofit

    init {
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    override fun baseUrl(): String {
        return "https://api.chucknorris.io/jokes/"
    }
}