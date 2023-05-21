package com.tomorrow.weatherapp.data.di

import com.tomorrow.weatherapp.data.repository.WeatherRepositoryImpl
import com.tomorrow.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
val DATA_MODULE = module {
    // repositories
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}
