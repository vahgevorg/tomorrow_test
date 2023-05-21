package com.tomorrow.weatherapp.feature.weatherforecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomorrow.weatherapp.core.extensions.flowOnBackground
import com.tomorrow.weatherapp.core.extensions.flowOnUIImmediate
import com.tomorrow.weatherapp.core.livedata.SingleLiveEvent
import com.tomorrow.weatherapp.core.livedata.asImmutable
import com.tomorrow.weatherapp.domain.interactors.GetWeatherForecastUseCase
import com.tomorrow.weatherapp.domain.model.LocationDomainModel
import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class WeatherForecastViewModel(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
) : ViewModel() {

    private val _locationData = MutableLiveData<LocationDomainModel>()
    val locationData = _locationData.asImmutable()

    private val _viewEffect = SingleLiveEvent<WeatherForecastViewEffect>()
    val viewEffect = _viewEffect.asImmutable()

    private fun collectWeatherForecast(latitude: Double, longitude: Double) {
        getWeatherForecastUseCase.execute(GetWeatherForecastUseCase.Params(latitude, longitude))
            .flowOnBackground()
            .onEach(::onWeatherForecastDataCollect)
            .catch {
                // NO-OP
            }
            .flowOnUIImmediate()
            .launchIn(viewModelScope)
    }

    private fun onWeatherForecastDataCollect(data: WeatherForecastDomainModel?) {
        // NO-OP
    }

    fun onLocationUpdateReceived(locationDomainModel: LocationDomainModel) {
        _locationData.postValue(locationDomainModel)
        _viewEffect.postValue(WeatherForecastViewEffect.ShowLoading)
    }
}
