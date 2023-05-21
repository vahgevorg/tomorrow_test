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
import com.tomorrow.weatherapp.core.extensions.viewBinding
import com.tomorrow.weatherapp.databinding.FragmentWeatherForecastBinding
import com.tomorrow.weatherapp.feature.base.BaseFragment
import com.tomorrow.weatherapp.service.LocationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class WeatherForecastFragment : BaseFragment() {

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
        // NO-OP
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
    }
}