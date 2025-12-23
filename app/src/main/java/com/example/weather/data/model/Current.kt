package com.example.weather.data.model

data class Current(
  val tempC: Float,
  val feelsLikeC: Float,
  val humidity: Int,
  val pressureMb: Float,
  val windKph: Float,
  val condition: Condition
)