package com.example.weatherappandroid

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

@Composable
fun HomeScreen(
    navigateToDetails: () -> Unit,
    weatherData: WeatherData?,
    lastLocation: LatLng?,
    selectedCity: String
) {
    val context = LocalContext.current

    val cityName = if (lastLocation != null) {
        getCityName(context, lastLocation.latitude, lastLocation.longitude)
    } else {
        selectedCity
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your current location: ${cityName ?: "Unknown"}")
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Home Screen", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = navigateToDetails) {
            Text(text = "Go to Details Screen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        weatherData?.let {
            WeatherCard(weatherData = it, selectedCity = selectedCity)
        }
    }
}

fun getCityName(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return if (addresses != null && addresses.isNotEmpty()) {
        addresses[0]?.locality
    } else {
        null
    }
}
