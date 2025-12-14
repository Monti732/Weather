package com.example.weather.ui

import android.os.Bundle
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.data.model.WeatherResponse

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
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
