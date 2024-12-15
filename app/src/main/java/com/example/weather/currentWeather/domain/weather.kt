package com.example.weather.currentWeather.domain

data class WeatherResponse(
    val location: Coordinate,
    val weather: List<Weather>,
    val temperature: Temperature,
    val wind: Wind,
    val rain: Rain?,
    val clouds: Int,
    val visibility: Int,
    val timestamp: Long,
    val country: String,
    val cityName: String,
    val sunrise: Long,
    val sunset: Long
)

data class Coordinate(
    val longitude: Double,
    val latitude: Double
)

data class Weather(
    val conditionId: Int,
    val mainCondition: String,
    val description: String,
    val iconUrl: String
)

data class Temperature(
    val current: Double,
    val feelsLike: Double,
    val min: Double,
    val max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val direction: Int,
    val gust: Double?
)

data class Rain(
    val lastHour: Double
)
