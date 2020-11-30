package com.nytimes.task.application

import android.app.Application
import com.nytimes.task.di.Configurator

class Application : Application() {
    private val configurator = Configurator()

    override fun onCreate() {
        super.onCreate()
        configurator.configureAppInit(applicationContext)
    }
}