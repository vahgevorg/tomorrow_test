package com.tomorrow.weatherapp.di.initializer

import android.content.Context
import com.tomorrow.weatherapp.data.di.DATA_MODULE
import com.tomorrow.weatherapp.data.di.NET_MODULE
import com.tomorrow.weatherapp.di.modules.APP_MODULE
import com.tomorrow.weatherapp.di.modules.VIEW_MODEL_MODULE
import com.tomorrow.weatherapp.domain.di.USE_CASE_MODULE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

@FlowPreview
@ExperimentalCoroutinesApi
object DependencyInjectionInitializer {

    fun initializeGraph(applicationContext: Context) {
        startKoin {
            androidContext(applicationContext)
            fragmentFactory()
            modules(
                listOf(
                    APP_MODULE,
                    VIEW_MODEL_MODULE,
                    DATA_MODULE,
                    NET_MODULE,
                    USE_CASE_MODULE,
                )
            )
        }
    }
}
