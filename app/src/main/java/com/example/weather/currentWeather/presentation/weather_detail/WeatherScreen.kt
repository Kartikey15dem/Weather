package com.example.weather.currentWeather.presentation.weather_detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.weather.currentWeather.models.GridItem
import com.example.weather.currentWeather.models.WeatherUI
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.R


var isCurrent = true
var cities = mutableListOf<String>( "New York", "Singapore", "Mumbai", "Delhi", "Sydney","Melbourne")
@Composable
fun WeatherScreen(
    weatherUI: WeatherUI?,
    onRefresh: () -> Unit,
    onClick : (String) -> Unit,
    onCurrent: () -> Unit
) {

    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.95f)
    if (weatherUI == null) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
        Button(onClick = onRefresh) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_refresh_24),
                contentDescription = "Your drawable description",
                modifier = Modifier.size(24.dp)
            )
        }}
    } else{
        LazyColumn(
            modifier = Modifier
                .wrapContentSize()
                .background(color = backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // City Name
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = weatherUI.cityName,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_refresh_24),
                        contentDescription = "Your drawable description",
                        modifier = Modifier.size(24.dp)
                            .padding(top = 5.dp)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
            }

            // Weather Info Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = weatherUI.temperature,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                modifier = Modifier
                                    .padding(end = 10.dp),
                                text = weatherUI.mainCondition,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 2.5.dp),
                                text = weatherUI.maxMinTemperature,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Description Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = weatherUI.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Rain and Cloud Info Card
            item {
                Column(
                    modifier = Modifier.padding(8.dp),

                    ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically // Align items vertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.mipmap.rain_foreground),
                                contentDescription = "Your drawable description",
                                modifier = Modifier.size(44.dp) // Adjust size as needed
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
                            Text(
                                text = "Rain: ${weatherUI.rainCondition}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start=0.2.dp,end=9.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically // Align items vertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.mipmap.clouds_foreground),
                                contentDescription = "Your drawable description",
                                modifier = Modifier.size(44.dp) // Adjust size as needed
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
                            Text(
                                text = "Clouds: ${weatherUI.cloudCondition}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 0.2.dp,end = 9.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // LazyVerticalGrid for Grid Items
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.wrapContentSize(),

                        ) {
                        items(weatherUI.GridItems) { gridItem ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Column {
                                        Image(
                                            painter = painterResource(id = gridItem.imageId),
                                            contentDescription = "Your drawable description",
                                            modifier = Modifier.size(44.dp) // Adjust size as needed
                                        )
                                        Log.d("GridItemsData", "Image ID: ${gridItem.imageId}, Title: ${gridItem.title}")

                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = gridItem.title,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = gridItem.value,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Sunrise and Sunset Card
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Row(modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Column (horizontalAlignment = Alignment.CenterHorizontally){
                            Icon(
                                painter = painterResource(id = R.mipmap.sunrise_foreground),
                                contentDescription = "Your drawable description",
                                modifier = Modifier.size(55.dp) // Adjust size as needed
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Sunrise",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = weatherUI.sunrise,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.mipmap.sunset_foreground),
                                contentDescription = "Your drawable description",
                                modifier = Modifier.size(55.dp),
                                // Adjust size as needed
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Sunset",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = weatherUI.sunset,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                            )
                        }
                    }
                }
            }
            if(!isCurrent){
            item{
                Spacer(modifier = Modifier.height(8.dp))
                Card ( modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onCurrent()},
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )){
                    Text(
                        text = "Current Location",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }}
            item{
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    for (city in cities) {
                        Card ( modifier = Modifier.fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onClick(city)},
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )){
                            Text(
                                text = city,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

fun click(city: String){}
fun refresh(){}
@PreviewLightDark
@Composable
fun WeatherScreenPreview() {
    WeatherTheme {
        WeatherScreen(onRefresh = ::refresh, onClick = ::click, onCurrent = ::refresh, weatherUI = mockWeather)
    }
}
val mockWeather = WeatherUI(
    cityName = "San Francisco",
    temperature = "18°C",
    mainCondition = "Cloudy",
    maxMinTemperature = "20°C / 15°C",
    description = "Light rain expected",
    rainCondition = "10%",
    cloudCondition = "75%",
    sunrise = "6:45 AM",
    sunset = "7:30 PM",
    GridItems = listOf(
        GridItem(R.mipmap.feelslike_foreground,"Feels like", "15 °C"),
        GridItem(R.mipmap.humidity_foreground,"Humidity", "26 %"),
        GridItem(R.mipmap.wind_foreground,"Wind", "14 m/s"),
        GridItem(R.mipmap.airpressure_foreground,"Pressure", "1015 hPa"),
        GridItem(R.mipmap.visibility2_foreground,"Visibility", "10 km"),
        GridItem(R.mipmap.gust_foreground,"Gust", "No gust")
    )
)