package com.example.weather.data.model

import com.example.weather.ui.Condition

data class Current(
  val temp_c: Float,
  val feelslike_c: Float,
  val humidity: Int,
  val pressure_mb: Float,
  val wind_kph: Float,
  val condition: Condition
)