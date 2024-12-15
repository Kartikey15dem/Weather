package com.example.weather.currentWeather.domain


import com.example.weather.core.domain.util.NetworkError
import com.example.weather.core.domain.util.Result

interface WeatherDataSource {
    suspend fun getWeatherResponse(lat : Double , long: Double): Result<WeatherResponse, NetworkError>
    suspend fun getWeatherResponse(city : String): Result<WeatherResponse, NetworkError>
}