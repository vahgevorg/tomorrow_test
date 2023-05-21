package com.tomorrow.weatherapp.di.modules

import com.tomorrow.weatherapp.AndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val APP_MODULE = module {
    single { androidContext() as AndroidApp }
}