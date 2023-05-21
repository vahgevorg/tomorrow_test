package com.tomorrow.weatherapp.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomorrow.weatherapp.core.common.Constants.DEFAULT_SPLASH_TIMEOUT
import com.tomorrow.weatherapp.core.livedata.SingleLiveEvent
import com.tomorrow.weatherapp.core.livedata.asImmutable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel : ViewModel() {

    private val _navigation = SingleLiveEvent<SplashRoutableScreen>()
    val navigation = _navigation.asImmutable()

    fun onViewModelReady() {
        viewModelScope.launch {
            delay(DEFAULT_SPLASH_TIMEOUT)
            _navigation.postValue(SplashRoutableScreen.WeatherForecastScreen)
        }
    }
}
