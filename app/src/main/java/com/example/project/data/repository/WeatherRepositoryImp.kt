package com.example.project.data.repository

import com.example.project.common.ApiResponse
import com.example.project.data.remote.WeatherDataSource
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.domain.model.forecast.Forecast
import com.example.project.domain.model.search.Geo
import com.example.project.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImp  @Inject constructor(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getWeather(lat: String, lon: String): ApiResponse {
        return dataSource.getWeather(lat = lat, lon = lon)
    }

    override suspend fun search(q: String, limit: Int): Pair<List<Geo>?, String?> {
        return dataSource.search(q = q, limit = limit)
    }

}