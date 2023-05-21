package com.tomorrow.weatherapp.feature.weatherforecast

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.tomorrow.weatherapp.R
import com.tomorrow.weatherapp.core.extensions.viewBinding
import com.tomorrow.weatherapp.databinding.FragmentWeatherForecastBinding
import com.tomorrow.weatherapp.domain.model.LocationDomainModel
import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel
import com.tomorrow.weatherapp.feature.base.BaseFragment
import com.tomorrow.weatherapp.service.LocationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class WeatherForecastFragment : BaseFragment() {

    private var locationUpdatesJob: Job? = null
    private var locationService: LocationService? = null
    private var isServiceBound = false

    private val binding by viewBinding(FragmentWeatherForecastBinding::inflate)
    private val weatherForecastViewModel: WeatherForecastViewModel by viewModel()
    private val locationServiceIntent by lazy {
        Intent(requireActivity(), LocationService::class.java)
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            locationService = binder.getService()
            isServiceBound = true

            // Subscribe to location updates
            locationService?.getLocationUpdates()?.onEach { location ->
                weatherForecastViewModel.onLocationUpdateReceived(location)
            }?.launchIn(lifecycleScope)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            locationService = null
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAndBindService()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAndUnBindService()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun observeViewModelValues() {
        weatherForecastViewModel.apply {
            locationData.observe(viewLifecycleOwner, ::onLocationData)
            viewEffect.observe(viewLifecycleOwner, ::onViewEffect)
            weatherForecast.observe(viewLifecycleOwner, ::onWeatherForecast)
        }
    }

    private fun startAndBindService() {
        // Start the location service
        requireActivity().startService(locationServiceIntent)
        // Bind to the location service
        requireActivity().bindService(locationServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopAndUnBindService() {
        // Unbind from the location service
        if (isServiceBound) {
            requireActivity().unbindService(serviceConnection)
            isServiceBound = false
        }
        // Stop the location service
        requireActivity().stopService(locationServiceIntent)
        // Cancel location updates job
        locationUpdatesJob?.cancel()
    }

    private fun onLocationData(locationDomainModel: LocationDomainModel?) {
        locationDomainModel ?: return
        binding.tvLocationValue.text = getString(R.string.weather_forecast_location_placeholder, locationDomainModel.latitude, locationDomainModel.longitude)
    }

    private fun onViewEffect(viewEffect: WeatherForecastViewEffect?) {
        viewEffect?.let {
            when (it) {
                WeatherForecastViewEffect.HideLoading -> applyHideLoadingState()
                WeatherForecastViewEffect.ShowLoading -> applyShowLoadingState()
            }
        }
    }

    private fun applyShowLoadingState() {
        binding.layoutLoading.globalLoadingContainer.isVisible = true
    }

    private fun applyHideLoadingState() {
        binding.layoutLoading.globalLoadingContainer.isVisible = false
    }

    private fun onWeatherForecast(weatherForecastDomainModel: WeatherForecastDomainModel?) {
        weatherForecastDomainModel ?: return
        binding.apply {
            tvWeatherForecastSubTitle.isVisible = true
            // TODO: create adapter and submit list here
        }
    }
}