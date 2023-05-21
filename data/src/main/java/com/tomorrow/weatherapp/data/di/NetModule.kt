package com.tomorrow.weatherapp.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tomorrow.weatherapp.core.common.Constants.API_HOST_URL
import com.tomorrow.weatherapp.data.service.WeatherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NET_MODULE = module {

    // gson
    single<Gson> { GsonBuilder().setLenient().serializeNulls().create() }
    single<GsonConverterFactory> { GsonConverterFactory.create(get<Gson>()) }

    // interceptors
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }

    // okhttp clients
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // retrofit clients
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_HOST_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get())
            .build()
    }

    // services
    single<WeatherApiService> { get<Retrofit>().create(WeatherApiService::class.java) }
}
