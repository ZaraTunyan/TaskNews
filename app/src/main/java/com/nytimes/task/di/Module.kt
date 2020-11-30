package com.nytimes.task.di

import com.nytimes.task.features.HomeViewModel
import com.nytimes.task.helpers.provideGson
import com.nytimes.task.helpers.provideHttpLoggingInterceptor
import com.nytimes.task.helpers.provideOkHttpClient
import com.nytimes.task.helpers.provideRetrofit
import com.nytimes.task.network.api.ApiService
import com.nytimes.task.network.api.ApiServiceRepository
import com.nytimes.task.helpers.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel


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
    factory { (key : String) -> HomeViewModel(get(), get(), key) }
}
