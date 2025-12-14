package com.example.weather.data.model

import com.example.weather.ui.Current
import com.example.weather.ui.Location

data class WeatherResponse(
  val location: Location,
  val current: Current
)
