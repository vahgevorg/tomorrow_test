package com.tomorrow.weatherapp.di.modules

import com.tomorrow.weatherapp.feature.splash.SplashViewModel
import com.tomorrow.weatherapp.feature.weatherforecast.WeatherForecastViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val VIEW_MODEL_MODULE = module {
    viewModel { SplashViewModel() }
    viewModel { WeatherForecastViewModel(get()) }
}
