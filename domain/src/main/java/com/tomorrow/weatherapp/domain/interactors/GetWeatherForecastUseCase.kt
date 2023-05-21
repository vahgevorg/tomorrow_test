package com.tomorrow.weatherapp.domain.interactors

import com.tomorrow.weatherapp.domain.interactors.base.FlowParameterizedUseCase
import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel
import com.tomorrow.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherForecastUseCase(
    private val weatherRepository: WeatherRepository,
) : FlowParameterizedUseCase<GetWeatherForecastUseCase.Params, WeatherForecastDomainModel>() {

    override fun execute(params: Params): Flow<WeatherForecastDomainModel> =
        weatherRepository.getWeatherForecast(params.latitude, params.longitude)

    data class Params(
        val latitude: Double,
        val longitude: Double,
    )
}
