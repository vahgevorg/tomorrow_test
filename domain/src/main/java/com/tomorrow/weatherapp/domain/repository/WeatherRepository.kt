package com.tomorrow.weatherapp.domain.repository

import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherForecast(latitude: Double, longitude: Double): Flow<WeatherForecastDomainModel>
}