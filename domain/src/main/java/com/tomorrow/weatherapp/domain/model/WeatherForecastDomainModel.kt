package com.tomorrow.weatherapp.domain.model

data class WeatherForecastDomainModel(
    val weatherUnits: HourlyWeatherUnitsDomainModel,
    val weatherForecast: List<HourlyWeatherDomainModel>,
)

data class HourlyWeatherUnitsDomainModel(
    val time: String,
    val temperature2m: String,
)

data class HourlyWeatherDomainModel(
    val time: String,
    val temperature2m: Double,
)