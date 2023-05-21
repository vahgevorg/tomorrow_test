package com.tomorrow.weatherapp.domain.di

import com.tomorrow.weatherapp.domain.interactors.GetWeatherForecastUseCase
import org.koin.dsl.module

val USE_CASE_MODULE = module {
    // weather forecast
    factory { GetWeatherForecastUseCase(get()) }
}
