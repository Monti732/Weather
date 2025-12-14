package com.example.weather.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.WeatherRepository
import com.example.weather.data.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
  private val repository = WeatherRepository()

  var weather by mutableStateOf<WeatherResponse?>(null)
    private set

  var error by mutableStateOf<String?>(null)
    private set

  fun load(city: String) {
    viewModelScope.launch {
      try {
        weather = repository.loadWeather(city)
        error = null

      } catch (e: Exception) {
        Log.e("TAG", e.message ?: "Ошибка", e)
        error = "Ошибка загрузки данных"
      }
    }
  }
}