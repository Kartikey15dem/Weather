package com.example.weather.currentWeather.data.mappers

import com.example.weather.currentWeather.data.networking.dto.MainDto
import com.example.weather.currentWeather.data.networking.dto.RainDto
import com.example.weather.currentWeather.data.networking.dto.WeatherDto
import com.example.weather.currentWeather.data.networking.dto.WeatherResponseDto
import com.example.weather.currentWeather.data.networking.dto.WindDto
import com.example.weather.currentWeather.domain.Coordinate
import com.example.weather.currentWeather.domain.Rain
import com.example.weather.currentWeather.domain.Temperature
import com.example.weather.currentWeather.domain.Weather
import com.example.weather.currentWeather.domain.WeatherResponse
import com.example.weather.currentWeather.domain.Wind

fun WeatherResponseDto.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        location = Coordinate(coord.lon, coord.lat),
        weather = weather.map { it.toWeather() } ,
        temperature = main.toTemperature(),
        wind = wind.toWind(),
        rain = rain?.toRain(),
        clouds = clouds.all,
        visibility = visibility,
        timestamp = dt,
        country = sys.country.toString(),
        cityName = name,
        sunrise = sys.sunrise,
        sunset = sys.sunset
    )
}

fun WeatherDto.toWeather(): Weather {
    return Weather(
        conditionId = id,
        mainCondition = main,
        description = description,
        iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png" // Constructing icon URL
    )
}

fun MainDto.toTemperature(): Temperature {
    return Temperature(
        current = temp,
        feelsLike = feels_like,
        min = temp_min,
        max = temp_max,
        pressure = pressure,
        humidity = humidity
    )
}

fun WindDto.toWind(): Wind {
    return Wind(
        speed = speed,
        direction = deg,
        gust = gust
    )
}

fun RainDto.toRain(): Rain {
    return Rain(
        lastHour = `1h`
    )
}
