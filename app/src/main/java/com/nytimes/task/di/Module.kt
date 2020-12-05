package com.nytimes.task.di

import com.nytimes.task.R
import com.nytimes.task.features.home.HomeViewModel
import com.nytimes.task.helpers.*
import com.nytimes.task.network.api.ApiService
import com.nytimes.task.network.api.ApiServiceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val DataModule = module {
    single {
        val retrofit: Retrofit = get()
        return@single retrofit.create(ApiService::class.java)
    }
    single { ApiServiceRepository(apiService = get()) }
    single { ErrorHandler(androidContext()) }
}

val NetworkModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(loggingInterceptor = get()) }
    single { provideRetrofit(client = get()) }
    single { provideGson() }
}

val PresentationModule = module {
    viewModel { HomeViewModel(get(), get(), androidContext().getString(R.string.nyt_api_key)) }
}
