package com.example.weather

import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.webkit.ConsoleMessage
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    /*if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())*/
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      WeatherScreen()
    }
  }
}

@Composable
fun WeatherInfo(data: WeatherResponse) {

  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text("Город: ${data.location.name}, ${data.location.country}")
    Text("Температура: ${data.current.temp_c} °C")
    Text("Ощущается: ${data.current.feelslike_c} °C")
    Text("Влажность: ${data.current.humidity} %")
    Text("Давление: ${data.current.pressure_mb} мб")
    Text("Ветер: ${data.current.wind_kph} км/ч")
    Text(data.current.condition.text)
  }
}



@Composable
@Preview(showBackground = true)
fun WeatherScreen(
  viewModel: WeatherViewModel = viewModel()
) {
  var city by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {

    OutlinedTextField(
      value = city,
      onValueChange = { city = it },
      label = { Text("Город") },
      modifier = Modifier.fillMaxWidth()
    )

    Button(
      onClick = { viewModel.load(city) },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Показать погоду")
    }

    viewModel.weather?.let { WeatherInfo(it) }

    viewModel.error?.let {
      Text(
        text = it,
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}



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
        //Timber.e(e, "Ошибка загрузки данных")

      } catch (e: Exception) {
        Log.e("TAG", e.message ?: "Ошибка", e)
        error = "Ошибка загрузки данных"
      }
    }
  }
}



class WeatherRepository {

  private val apiKey = "cd3283213d9841639cc90416251412"

  suspend fun loadWeather(city: String): WeatherResponse {
    return RetrofitClient.api.getCurrentWeather(apiKey, city)
  }
}


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



interface WeatherApi {

  @GET("current.json")
  suspend fun getCurrentWeather(
    @Query("key") apiKey: String,
    @Query("q") city: String,
    @Query("lang") lang: String = "ru"
  ): WeatherResponse
}



data class WeatherResponse(
  val location: Location,
  val current: Current
)

data class Location(
  val name: String,
  val country: String
)

data class Current(
  val temp_c: Float,
  val feelslike_c: Float,
  val humidity: Int,
  val pressure_mb: Float,
  val wind_kph: Float,
  val condition: Condition
)

data class Condition(
  val text: String
)

