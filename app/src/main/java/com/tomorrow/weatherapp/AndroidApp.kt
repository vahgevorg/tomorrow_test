package com.tomorrow.weatherapp

import android.app.Application
import com.tomorrow.weatherapp.di.initializer.DependencyInjectionInitializer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyInjectionInitializer.initializeGraph(this)
    }
}