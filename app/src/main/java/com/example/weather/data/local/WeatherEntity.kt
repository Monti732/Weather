package com.example.weather.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
  @PrimaryKey val city: String,
  val country: String,
  val tempC: Float,
  val feelsLikeC: Float,
  val humidity: Int,
  val pressureMb: Float,
  val windKph: Float,
  val conditionText: String,
  val timestamp: Long = System.currentTimeMillis()
)

