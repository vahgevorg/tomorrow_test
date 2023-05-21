package com.tomorrow.weatherapp.data.mapper

import com.tomorrow.weatherapp.core.mapper.Mapper
import com.tomorrow.weatherapp.data.dto.HourlyWeatherUnitsApiDto
import com.tomorrow.weatherapp.data.dto.WeatherApiDto
import com.tomorrow.weatherapp.domain.model.HourlyWeatherDomainModel
import com.tomorrow.weatherapp.domain.model.HourlyWeatherUnitsDomainModel
import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel

object WeatherDataMapper {

    val weatherApiDtoToDomainModel = object :
        Mapper<WeatherApiDto, WeatherForecastDomainModel> {
        override fun map(from: WeatherApiDto): WeatherForecastDomainModel =
            WeatherForecastDomainModel(
                weatherUnits = from.hourlyWeatherUnits?.let {
                    hourlyWeatherUnitsApiDtoToDomainModel.map(
                        it
                    )
                } ?: HourlyWeatherUnitsDomainModel("", ""),
                weatherForecast = from.hourlyWeather?.let {
                    it.time?.zip(
                        it.temperature2m ?: emptyList()
                    ) { time, temperature -> HourlyWeatherDomainModel(time, temperature) }
                        ?: emptyList()
                } ?: emptyList(),
            )
    }

    val hourlyWeatherUnitsApiDtoToDomainModel = object :
        Mapper<HourlyWeatherUnitsApiDto, HourlyWeatherUnitsDomainModel> {
        override fun map(from: HourlyWeatherUnitsApiDto): HourlyWeatherUnitsDomainModel =
            HourlyWeatherUnitsDomainModel(
                time = from.time.orEmpty(),
                temperature2m = from.temperature2m.orEmpty(),
            )
    }
}
