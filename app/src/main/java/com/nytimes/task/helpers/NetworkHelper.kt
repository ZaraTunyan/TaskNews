package com.nytimes.task.helpers

import com.google.gson.Gson
import com.nytimes.task.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://api.nytimes.com/svc/mostpopular/"
private const val API_VERSION = "v2/"

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {  level = HttpLoggingInterceptor.Level.BODY }

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL + API_VERSION)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

fun provideGson(): Gson = Gson()