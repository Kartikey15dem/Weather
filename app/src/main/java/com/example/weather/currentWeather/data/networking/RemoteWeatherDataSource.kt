package com.example.weather.currentWeather.data.networking


import com.example.weather.core.data.networking.constructUrl
import com.example.weather.core.data.networking.safeCall
import com.example.weather.core.domain.util.NetworkError
import com.example.weather.core.domain.util.Result
import com.example.weather.core.domain.util.map
import com.example.weather.currentWeather.data.mappers.toWeatherResponse
import com.example.weather.currentWeather.data.networking.dto.WeatherResponseDto
import com.example.weather.currentWeather.domain.WeatherDataSource
import com.example.weather.currentWeather.domain.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class RemoteWeatherDataSource (
    private val httpClient: HttpClient,
    private var coord : LocationDto = LocationDto("",mapOf("a" to "b"),0.0,0.0)
): WeatherDataSource {
    override suspend fun getWeatherResponse(
        lat: Double,
        long: Double
    ): Result<WeatherResponse, NetworkError> {
        return safeCall<WeatherResponseDto> {
            httpClient.get(
                urlString = constructUrl(lat,long)
            )
        }.map {  response ->
            response.toWeatherResponse() }
    }

    override suspend fun getWeatherResponse(city: String): Result<WeatherResponse, NetworkError> {

        safeCall<List<LocationDto>> {
            httpClient.get(
                urlString = constructUrl(city)
            )
        }.map {  response ->
            coord =  response.first()
        }
        return getWeatherResponse(coord.lat,coord.lon)
    }
}
@Serializable
data class LocationDto(
    val name: String, // The name of the city
    @SerialName("local_names")
    val localNames: Map<String, String>? = null, // Localized names of the city
    val lat: Double, // Latitude of the location
    val lon: Double  // Longitude of the location
)



