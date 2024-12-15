package com.example.weather.currentWeather.models

import com.example.weather.currentWeather.domain.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.weather.R

data class WeatherUI(
    val cityName: String,
    val temperature: String,
    val mainCondition: String,
    val maxMinTemperature: String,
    val description: String,
    val rainCondition: String,
    val cloudCondition: String,
    val sunrise: String,
    val sunset: String,
    val GridItems: List<GridItem>
)
fun WeatherResponse.toWeatherUI(unit: String = "metric"): WeatherUI {
    val dateFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val tempUnit = if (unit == "metric") "°C" else if (unit == "imperial") "°F" else "K"
    val windUnit = if (unit == "imperial") "mph" else "m/s"
    var feelsLike = "${temperature.feelsLike}$tempUnit"
    var humidity = "${temperature.humidity}%"
    var wind = "${wind.speed} $windUnit°"
    var pressure = "${temperature.pressure} hPa"
    var visibility = "${visibility / 1000} km"
    var gust = this.wind.gust?.let { "$it $windUnit" } ?: "No gust"
    var weather = this.weather.first()

    return WeatherUI(
        cityName = cityName,
        temperature = "${temperature.current.toInt()}$tempUnit",
        mainCondition = weather.mainCondition,
        maxMinTemperature = "${temperature.max.toInt()}$tempUnit / ${temperature.min.toInt()}$tempUnit",
        description = weather.description,
        rainCondition = rain?.let { "${it.lastHour} mm rain in the last hour" } ?: "No rain",
        cloudCondition = "$clouds% cloud cover",
        sunrise = dateFormatter.format(sunrise * 1000L),
        sunset = dateFormatter.format(sunset * 1000L),
        GridItems = listOf(
            GridItem(R.mipmap.feelslike_foreground,"Feels like", feelsLike),
            GridItem(R.mipmap.humidity_foreground,"Humidity", humidity),
            GridItem(R.mipmap.wind_foreground,"Wind", wind),
            GridItem(R.mipmap.airpressure_foreground,"Pressure", pressure),
            GridItem(R.mipmap.visibility2_foreground,"Visibility", visibility),
            GridItem(R.mipmap.gust_foreground,"Gust", gust)
        )

    )
}

     data class GridItem(
         val imageId : Int,
         val title : String,
         val value : String
             )
