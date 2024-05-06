package com.example.weatherappandroid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherCard(weatherData: WeatherData, selectedCity: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Selected City: $selectedCity")
            Text(text = "Temperature: ${weatherData.temperature}°C")
            Text(text = "Weather Condition: ${weatherData.weatherCondition}")
            Text(text = "Wind Speed: ${weatherData.windSpeed} km/h")
        }
    }
}
