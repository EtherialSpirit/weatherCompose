package com.example.weathercompose.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi{
    @GET("weather")
    suspend fun getWeatherCity(
        @Query("id")id: String,
        @Query("units") unit: String = "metric"
    ): WeatherData

}
