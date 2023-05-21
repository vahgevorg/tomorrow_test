package com.tomorrow.weatherapp.feature.weatherforecast

sealed class WeatherForecastViewEffect {
    object ShowLoading : WeatherForecastViewEffect()
    object HideLoading : WeatherForecastViewEffect()
    object GenericError : WeatherForecastViewEffect()
}