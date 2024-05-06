package com.example.weatherappandroid

import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.annotations.SerializedName

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): WeatherDataResponse
}

data class WeatherDataResponse(
    @SerializedName("current") val current: CurrentWeather
)

data class CurrentWeather(
    @SerializedName("temp_c") val temperature: Double,
    @SerializedName("condition") val condition: WeatherCondition,
    @SerializedName("wind_kph") val windSpeed: Double
)

data class WeatherCondition(
    @SerializedName("text") val description: String
)
