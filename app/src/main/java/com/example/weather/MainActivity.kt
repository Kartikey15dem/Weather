package com.example.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.weather.core.data.location.getCoordinates


import com.example.weather.currentWeather.presentation.weather_detail.WeatherScreen
import com.example.weather.currentWeather.presentation.weather_detail.WeatherViewModel
import com.example.weather.currentWeather.presentation.weather_detail.cities
import com.example.weather.currentWeather.presentation.weather_detail.isCurrent
import com.example.weather.ui.theme.WeatherTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

var isNetwork = true
lateinit var coordinates: Pair<Double, Double>
class MainActivity : ComponentActivity() {

    val LOCATION_PERMISSION_REQUEST_CODE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)

        } else {}
            isNetwork = isNetworkAvailable(this)

            lifecycleScope.launch {
                 coordinates = getCoordinates(this@MainActivity)!!
                coordinates.let {
                    setContent {
                        WeatherTheme {
                            WeatherScreenWrapper(it.first, it.second)
                        }
                    }
                }

        }




    }
    fun onCityClick(city: String){}
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}

@Composable
fun WeatherScreenWrapper(
    lat: Double,
    long: Double,
    viewModel: WeatherViewModel = koinViewModel() // Use DI or create an instance manually
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather(lat, long) // Fetch data when screen loads
    }

    val weatherUI by viewModel.weatherUI.collectAsState()

    WeatherScreen(
        weatherUI = weatherUI,
        onRefresh = {
            scope.launch {
                viewModel.fetchWeather(lat, long) // Refresh data on button click
            }
        },
        onClick = { city ->
            scope.launch {
                viewModel.fetchWeather(city)
                cities = mutableListOf<String>( "New York", "Singapore", "Mumbai", "Delhi", "Sydney","Melbourne")
                cities.remove(city)
                isCurrent = false
            }
        },
                onCurrent = {
            scope.launch {
                viewModel.fetchWeather(coordinates.first, coordinates.second)
                isCurrent = true// Refresh data on button click
            }
        }
    )
}

@Composable
fun WeatherScreenWrapper(
    city: String,
    viewModel: WeatherViewModel = koinViewModel() // Use DI or create an instance manually
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather(city) // Fetch data when screen loads
    }

    val weatherUI by viewModel.weatherUI.collectAsState()

    WeatherScreen(
        weatherUI = weatherUI,
        onRefresh = {
            scope.launch {
                viewModel.fetchWeather(city) // Refresh data on button click
            }
        },
        onClick = { city ->
            scope.launch {
                viewModel.fetchWeather(city)
                cities = mutableListOf<String>( "New York", "Singapore", "Mumbai", "Delhi", "Sydney","Melbourne")
                cities.remove(city)
                isCurrent = false
            }
        },
                onCurrent = {
            scope.launch {
                viewModel.fetchWeather(coordinates.first, coordinates.second)
                isCurrent = true// Refresh data on button click
            }
        },
    )
}



