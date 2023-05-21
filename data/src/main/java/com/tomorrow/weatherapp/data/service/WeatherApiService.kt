package com.tomorrow.weatherapp.data.service

import com.tomorrow.weatherapp.data.dto.WeatherApiDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m",
    ): Response<WeatherApiDto>
}