package com.example.weather.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun weatherDao(): WeatherDao

  companion object {
    @Volatile private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase =
      instance ?: synchronized(this) {
        instance ?: Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "weather_db"
        ).build().also { instance = it }
      }
  }
}
