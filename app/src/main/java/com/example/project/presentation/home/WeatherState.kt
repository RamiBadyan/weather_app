package com.example.project.presentation.home

import com.example.project.domain.model.air_pollution.AirPollution
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.domain.model.forecast.Forecast

data class WeatherState(
    val currentWeather: CurrentWeather? = null,
    val forecast: Forecast? = null,
    val airPollution: AirPollution? = null,
    val error: String? = null,
    val loading: Boolean = false,
    val infoDialog: Boolean = false,
)
