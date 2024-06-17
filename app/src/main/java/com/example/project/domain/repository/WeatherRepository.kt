package com.example.project.domain.repository

import com.example.project.common.ApiResponse
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.domain.model.forecast.Forecast
import com.example.project.domain.model.search.Geo

interface WeatherRepository {

    suspend fun getWeather(lat: String, lon: String): ApiResponse

    suspend fun search(q: String, limit: Int): Pair<List<Geo>?, String?>

}