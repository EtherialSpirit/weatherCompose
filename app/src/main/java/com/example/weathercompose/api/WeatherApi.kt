package com.example.weathercompose.api

import retrofit2.http.GET

interface WeatherApi{
    @GET("/weather")
    fun getWeatherCity(): WeatherData
}