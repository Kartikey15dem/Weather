package com.example.weather.core.data.networking


import com.example.weather.BuildConfig
import java.util.Locale

fun constructUrl(lat: Double, lon: Double): String {
    return BuildConfig.BASE+"lat=$lat&lon=$lon"+ "&appid=be135256887942691af2de4367eec109&units=metric"
}

fun constructUrl(city :String): String {
    return "http://api.openweathermap.org/geo/1.0/direct?q=${city}&limit=1&appid=be135256887942691af2de4367eec109"
}

