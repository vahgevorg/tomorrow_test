package com.tomorrow.weatherapp.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomorrow.weatherapp.core.extensions.viewBinding
import com.tomorrow.weatherapp.databinding.FragmentSplashBinding
import com.tomorrow.weatherapp.feature.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SplashFragment : BaseFragment() {

    private val splashViewModel: SplashViewModel by viewModel()
    private val binding by viewBinding(FragmentSplashBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun observeViewModelValues() {
        splashViewModel.apply {
            navigation.observe(viewLifecycleOwner, ::onNavigation)
            onViewModelReady()
        }
    }

    private fun onNavigation(screen: SplashRoutableScreen) {
        when (screen) {
            SplashRoutableScreen.WeatherScreen -> {
                navigateToWeatherScreen()
            }
        }
    }

    private fun navigateToWeatherScreen() {
        // NO-OP
    }
}