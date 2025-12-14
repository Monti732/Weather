package com.example.weather.data.network

import com.example.weather.ui.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

  private const val BASE_URL = "https://api.weatherapi.com/v1/"

  val api: WeatherApi by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(WeatherApi::class.java)
  }
}