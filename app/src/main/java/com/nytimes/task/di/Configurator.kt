package com.nytimes.task.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Configurator {
    private val appComponent = listOf(
        DataModule,
        NetworkModule,
        PresentationModule
    )

    fun configureAppInit(context: Context) {
        startKoin {
            androidContext(context)
            modules(appComponent)
        }
    }
}