package com.nytimes.task.helpers

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
        .client(client)
        .build()

fun provideGson(): Gson = Gson()