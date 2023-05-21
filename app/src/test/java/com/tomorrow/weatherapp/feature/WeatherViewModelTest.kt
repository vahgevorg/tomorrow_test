package com.tomorrow.weatherapp.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.tomorrow.weatherapp.domain.interactors.GetWeatherForecastUseCase
import com.tomorrow.weatherapp.domain.model.HourlyWeatherDomainModel
import com.tomorrow.weatherapp.domain.model.HourlyWeatherUnitsDomainModel
import com.tomorrow.weatherapp.domain.model.LocationDomainModel
import com.tomorrow.weatherapp.domain.model.WeatherForecastDomainModel
import com.tomorrow.weatherapp.feature.weatherforecast.WeatherForecastViewEffect
import com.tomorrow.weatherapp.feature.weatherforecast.WeatherForecastViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getWeatherForecastUseCase: GetWeatherForecastUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: WeatherForecastViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onLocationUpdateReceived should update locationData and trigger data collection`() = runTest {
        // Initialize view model
        initViewModel()

        // Create a mock Observer to listen to the viewEffect LiveData
        val observer = mock(Observer::class.java) as Observer<WeatherForecastViewEffect>
        viewModel.viewEffect.observeForever(observer)

        // Mock the weather forecast and location
        val locationDomainModel = LocationDomainModel(52.520008, 13.404954)
        val weatherForecastDomainModel = WeatherForecastDomainModel(
            weatherUnits = HourlyWeatherUnitsDomainModel(
                "iso8601", "°C"
            ),
            weatherForecast = listOf(
                HourlyWeatherDomainModel(
                    "2023-05-20T00:00", 11.1
                )
            )
        )

        // Mock the weather forecast response from the use case
        whenever(getWeatherForecastUseCase.execute(any())) doReturn flowOf(weatherForecastDomainModel)

        // Call the onLocationUpdateReceived function
        viewModel.onLocationUpdateReceived(locationDomainModel)

        // Verify the view model location values
        assertThat(viewModel.locationData.value).isEqualTo(locationDomainModel)

        // Verify the view model weather forecast values
        assertThat(viewModel.weatherForecast.value).isEqualTo(weatherForecastDomainModel)

        // Cleanup the observer
        viewModel.viewEffect.removeObserver(observer)
    }

    @Test
    fun `onLocationUpdateReceived should show generic error when data collection fails`() = runTest {
        // Initialize view model
        initViewModel()

        // Create a mock Observer to listen to the viewEffect LiveData
        val observer = mock(Observer::class.java) as Observer<WeatherForecastViewEffect>
        viewModel.viewEffect.observeForever(observer)

        // Mock the location
        val locationDomainModel = LocationDomainModel(52.520008, 13.404954)

        // Mock the weather forecast response from the use case, throw exception
        whenever(getWeatherForecastUseCase.execute(any())) doReturn flow { throw Exception() }

        // Call the onLocationUpdateReceived function
        viewModel.onLocationUpdateReceived(locationDomainModel)

        // Verify that the viewEffect LiveData emitted the expected values
        verify(observer, times(1)).onChanged(WeatherForecastViewEffect.ShowLoading)
        verify(observer, times(1)).onChanged(WeatherForecastViewEffect.GenericError)

        // Cleanup the observer
        viewModel.viewEffect.removeObserver(observer)
    }

    private fun initViewModel() {
        viewModel = WeatherForecastViewModel(getWeatherForecastUseCase)
    }
}