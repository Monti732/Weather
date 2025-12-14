package com.example.weather.repository

import com.example.weather.RetrofitClient
import com.example.weather.WeatherResponse

class WeatherRepository {

  private val apiKey = "cd3283213d9841639cc90416251412"

  suspend fun loadWeather(city: String): WeatherResponse {
    return RetrofitClient.api.getCurrentWeather(apiKey, city)
  }
}