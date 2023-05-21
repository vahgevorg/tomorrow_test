package com.tomorrow.weatherapp.data.repository

import com.tomorrow.weatherapp.data.mapper.WeatherDataMapper.weatherApiDtoToDomainModel
import com.tomorrow.weatherapp.data.repository.base.BaseRemoteRepository
import com.tomorrow.weatherapp.data.service.WeatherApiService
import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel
import com.tomorrow.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@FlowPreview
class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
) : BaseRemoteRepository(), WeatherRepository {

    override fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Flow<WeatherForecastDomainModel> = flow {
        emit(weatherApiService.getWeatherForecast(latitude, longitude))
    }.flatMapMerge {
        parseResponse(it)
    }.map {
        weatherApiDtoToDomainModel.map(it)
    }
}