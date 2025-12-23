package com.example.weather.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.local.AppDatabase
import com.example.weather.data.local.WeatherEntity
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = WeatherRepository(AppDatabase.getInstance(application).weatherDao())

  var weather by mutableStateOf<WeatherEntity?>(null)
    private set

  var error by mutableStateOf<String?>(null)
    private set

  fun load(city: String) {
    viewModelScope.launch {
      val result = repository.getWeather(city)
      if (result != null) {
        weather = result
        error = null
      } else {
        error = "Нет данных"
      }
    }
  }
}
