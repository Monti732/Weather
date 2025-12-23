package com.example.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(weather: WeatherEntity)

  @Query("SELECT * FROM weather WHERE city = :city")
  suspend fun getWeather(city: String): WeatherEntity?
}
