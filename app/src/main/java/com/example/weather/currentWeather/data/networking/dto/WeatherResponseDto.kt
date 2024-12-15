package com.example.weather.currentWeather.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val coord: CoordDto,
    val weather: List<WeatherDto>,
    val base: String,
    val main: MainDto,
    val visibility: Int,
    val wind: WindDto,
    val rain: RainDto? = null, // Nullable as it may not always be present
    val clouds: CloudsDto,
    val dt: Long,
    val sys: SysDto,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

@Serializable
data class CoordDto(
    val lon: Double,
    val lat: Double
)

@Serializable
data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class MainDto(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int? = null,  // Nullable as it may not always be present
    val grnd_level: Int? = null // Nullable as it may not always be present
)

@Serializable
data class WindDto(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null // Nullable as it may not always be present
)

@Serializable
data class RainDto(
    val `1h`: Double // `1h` is a reserved word, so we use backticks
)

@Serializable
data class CloudsDto(
    val all: Int
)

@Serializable
data class SysDto(
    val type: Int? = null,
    val id: Int? = null,
    val country: String?=null,
    val sunrise: Long,
    val sunset: Long
)
