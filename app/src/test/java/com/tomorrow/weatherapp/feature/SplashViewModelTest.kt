package com.tomorrow.weatherapp.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomorrow.weatherapp.core.common.Constants.DEFAULT_SPLASH_TIMEOUT
import com.tomorrow.weatherapp.feature.splash.SplashRoutableScreen
import com.tomorrow.weatherapp.feature.splash.SplashViewModel
import com.tomorrow.weatherapp.testcore.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SplashViewModel

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
    fun `when onViewModelReady is called, navigation LiveData should emit WeatherForecastScreen after delay`() =
        runTest {
            // Initialize view model
            initViewModel()

            // Call the onViewModelReady function
            viewModel.onViewModelReady()

            // Wait for the delay to finish
            delay(DEFAULT_SPLASH_TIMEOUT)

            advanceUntilIdle()

            assertThat(
                viewModel.navigation.getOrAwaitValue(),
                CoreMatchers.instanceOf(SplashRoutableScreen.WeatherForecastScreen::class.java)
            )
        }

    private fun initViewModel() {
        viewModel = SplashViewModel()
    }
}