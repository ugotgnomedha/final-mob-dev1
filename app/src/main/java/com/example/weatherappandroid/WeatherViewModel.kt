package com.example.weatherappandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherApiService::class.java)

    fun fetchWeatherData(city: String) {
        viewModelScope.launch {
            val response = try {
                service.getCurrentWeather("6a675ca1eca24d08a27131407231712", city)
            } catch (error: Exception) {
                println("Error fetching weather data: $error")
                null
            }

            response?.let {
                val currentWeather = it.current
                val weatherData = WeatherData(
                    temperature = currentWeather.temperature,
                    weatherCondition = currentWeather.condition.description,
                    windSpeed = currentWeather.windSpeed
                )
                _weatherData.value = weatherData
            }
        }
    }
}
