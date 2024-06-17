package com.example.project.domain.use_cases

import com.example.project.common.ApiResponse
import com.example.project.common.Resource
import com.example.project.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase  @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(lat: String, lon: String): Flow<Resource<ApiResponse>> = flow {
        try {
            emit(Resource.Loading())
            val weather = repository.getWeather(lat = lat, lon = lon)
            emit(Resource.Success(weather))
        } catch (e: Exception) {
            try {
                emit(Resource.Error(e.localizedMessage ?: "An expected error!"))
            } catch (_: Exception) { }
        }
    }
}