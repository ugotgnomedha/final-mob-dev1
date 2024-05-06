package com.example.weatherappandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherappandroid.ui.theme.WeatherAppAndroidTheme
import kotlinx.coroutines.launch

import com.google.android.gms.maps.model.LatLng

private fun locationToLatLng(location: Location): LatLng {
    return LatLng(location.latitude, location.longitude)
}

class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    private var selectedCity by mutableStateOf("London")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppAndroidTheme {
                val navController = rememberNavController()

                LaunchedEffect(key1 = Unit) {
                    viewModel.fetchWeatherData(selectedCity)
                }

                val navigateToDetails: () -> Unit = {
                    navController.navigate("details")
                }

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        val context = LocalContext.current
                        val lastLocation = getLastKnownLocation(context)
                        val weatherData by viewModel.weatherData.collectAsState()

                        if (lastLocation != null) {
                            HomeScreen(
                                navigateToDetails = navigateToDetails,
                                weatherData = weatherData,
                                lastLocation = locationToLatLng(lastLocation),
                                selectedCity = selectedCity
                            )
                        } else {
                            // location is not available.
                        }
                    }
                    composable("details") {
                        DetailsScreen(
                            onCitySelected = { city ->
                                viewModel.fetchWeatherData(city)
                                selectedCity = city
                                navController.popBackStack()
                            },
                            navigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(context: Context): Location? {
        return if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            null
        }
    }
}
