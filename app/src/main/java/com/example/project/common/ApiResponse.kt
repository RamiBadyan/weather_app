package com.example.project.common

import com.example.project.domain.model.air_pollution.AirPollution
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.domain.model.forecast.Forecast

data class ApiResponse(
    val currentWeather: Pair<CurrentWeather?, String?>,
    val forecast: Pair<Forecast?, String?>,
    val airPollution: Pair<AirPollution?, String?>,
)
