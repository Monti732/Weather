package com.example.weather.repository

import com.example.weather.data.local.WeatherDao
import com.example.weather.data.local.WeatherEntity
import com.example.weather.data.network.RetrofitClient

class WeatherRepository(private val dao: WeatherDao) {

  private val apiKey = "cd3283213d9841639cc90416251412"

  suspend fun getWeather(city: String): WeatherEntity? {
    val cached = dao.getWeather(city)
    val now = System.currentTimeMillis()
    val cacheValid = cached != null && now - cached.timestamp < 10 * 60 * 1000 // 10 минут

    if (cacheValid) {
      return cached
    }

    return try {
      val response = RetrofitClient.api.getCurrentWeather(apiKey, city)
      val entity = WeatherEntity(
        city = response.location.name,
        country = response.location.country,
        tempC = response.current.tempC,
        feelsLikeC = response.current.feelsLikeC,
        humidity = response.current.humidity,
        pressureMb = response.current.pressureMb,
        windKph = response.current.windKph,
        conditionText = response.current.condition.text,
        timestamp = now
      )
      dao.insert(entity) 
      entity
    } catch (e: Exception) {
      cached
    }
  }
}