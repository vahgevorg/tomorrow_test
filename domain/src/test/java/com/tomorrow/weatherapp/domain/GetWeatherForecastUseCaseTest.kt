package com.tomorrow.weatherapp.domain

import com.tomorrow.weatherapp.domain.interactors.GetWeatherForecastUseCase
import com.tomorrow.weatherapp.domain.model.LocationDomainModel
import com.tomorrow.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetWeatherForecastUseCaseTest {

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var useCase: GetWeatherForecastUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initUseCase() {
        useCase = GetWeatherForecastUseCase(
            weatherRepository
        )
    }

    @Test
    fun `execute should call getWeatherForecast on repository`() = runTest {
        // Initialize use case
        initUseCase()

        // Mock the location
        val locationDomainModel = LocationDomainModel(52.520008, 13.404954)

        // Execute the use case
        useCase.execute(
            GetWeatherForecastUseCase.Params(
                locationDomainModel.latitude,
                locationDomainModel.longitude
            )
        )

        // Verify that the `getWeatherForecast` method on the repository was called with the correct parameters
        verify(weatherRepository).getWeatherForecast(
            locationDomainModel.latitude,
            locationDomainModel.longitude
        )
    }
}
