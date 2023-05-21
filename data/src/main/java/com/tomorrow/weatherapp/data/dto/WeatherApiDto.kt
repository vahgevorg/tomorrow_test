package com.tomorrow.weatherapp.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherApiDto(
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("generationtime_ms") var generationTimeMs: Double?,
    @SerializedName("utc_offset_seconds") var utcOffsetSeconds: Int?,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_abbreviation") var timezoneAbbreviation: String?,
    @SerializedName("elevation") var elevation: Int?,
    @SerializedName("hourly_units") var hourlyWeatherUnits: HourlyWeatherUnitsApiDto?,
    @SerializedName("hourly") var hourlyWeather: HourlyWeatherApiDto?,
)

data class HourlyWeatherUnitsApiDto(
    @SerializedName("time") var time: String?,
    @SerializedName("temperature_2m") var temperature2m: String?,
)

data class HourlyWeatherApiDto(
    @SerializedName("time") var time: ArrayList<String>?,
    @SerializedName("temperature_2m") var temperature2m: ArrayList<Double>?,
)
