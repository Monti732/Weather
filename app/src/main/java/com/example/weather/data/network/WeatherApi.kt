package com.example.weather.data.network

import com.example.weather.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

  @GET("current.json")
  suspend fun getCurrentWeather(
    @Query("key") apiKey: String,
    @Query("q") city: String,
    @Query("lang") lang: String = "ru"
  ): WeatherResponse
}