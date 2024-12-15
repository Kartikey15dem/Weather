package com.example.weather.di

import com.example.weather.core.data.networking.HttpClientFactory
import com.example.weather.currentWeather.data.networking.LocationDto
import com.example.weather.currentWeather.data.networking.RemoteWeatherDataSource
import com.example.weather.currentWeather.domain.WeatherDataSource
import com.example.weather.currentWeather.presentation.weather_detail.WeatherPreferences
import com.example.weather.currentWeather.presentation.weather_detail.WeatherViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf


val appModule = module {
    // Provide an HttpClient instance
    single { HttpClientFactory.create(CIO.create()) }

    // Provide RemoteWeatherDataSource as WeatherDataSource
    single<WeatherDataSource> { RemoteWeatherDataSource(get(),  LocationDto("",mapOf("a" to "b"),0.0,0.0)) }

    // Provide WeatherPreferences
    single { WeatherPreferences(androidContext()) } // Add this line

    // Provide WeatherViewModel
    viewModelOf(::WeatherViewModel)
}
