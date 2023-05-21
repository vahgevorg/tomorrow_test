package com.tomorrow.weatherapp.feature.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomorrow.weatherapp.core.extensions.viewBinding
import com.tomorrow.weatherapp.databinding.FragmentWeatherForecastBinding
import com.tomorrow.weatherapp.feature.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class WeatherForecastFragment : BaseFragment() {

    private val binding by viewBinding(FragmentWeatherForecastBinding::inflate)
    private val weatherForecastViewModel: WeatherForecastViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun observeViewModelValues() {
        // NO-OP
    }
}