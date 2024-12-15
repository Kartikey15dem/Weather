package com.example.weather.currentWeather.presentation.weather_detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.weather.currentWeather.domain.WeatherDataSource
import com.example.weather.currentWeather.domain.WeatherResponse
import com.example.weather.currentWeather.models.WeatherUI
import com.example.weather.currentWeather.models.toWeatherUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.weather.core.domain.util.Result
import com.example.weather.isNetwork

class WeatherViewModel(
    private val remoteWeatherDataSource: WeatherDataSource,
    private val weatherPreferences: WeatherPreferences
) : ViewModel() {

    private val _weatherUI = MutableStateFlow<WeatherUI?>(null)
    val weatherUI: StateFlow<WeatherUI?> get() = _weatherUI

    init {
        // Load saved data on initialization
        viewModelScope.launch {
            weatherPreferences.weatherFlow.collect { savedWeather ->
                _weatherUI.value = savedWeather
            }
        }
    }

    fun fetchWeather(lat: Double, long: Double) {
        if (isNetwork){
            viewModelScope.launch {
                when (val result = remoteWeatherDataSource.getWeatherResponse(lat, long)) {
                    is Result.Error -> {
                        _weatherUI.value = null
                    }
                    is Result.Success -> {
                        val weatherUI = result.data.toWeatherUI()
                        _weatherUI.value = weatherUI
                        // Save fetched data
                        weatherPreferences.saveWeather(weatherUI)
                    }
                }
            }
        }
    }

    fun fetchWeather(city : String) {
        if (isNetwork){
            viewModelScope.launch {
                when (val result = remoteWeatherDataSource.getWeatherResponse(city)) {
                    is Result.Error -> {
                        _weatherUI.value = null
                    }
                    is Result.Success -> {
                        val weatherUI = result.data.toWeatherUI()
                        _weatherUI.value = weatherUI
                        // Save fetched data
                        weatherPreferences.saveWeather(weatherUI)
                    }
                }
            }
        }
    }
}


