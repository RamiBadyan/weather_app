package com.example.project.domain.use_cases

import com.example.project.common.Resource
import com.example.project.domain.model.search.Geo
import com.example.project.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase  @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(q: String, limit: Int): Flow<Resource<List<Geo>>> = flow {
        try {
            emit(Resource.Loading())
            val geo = repository.search(q = q, limit = limit)
            if (geo.second != null) {
                emit(Resource.Error(geo.second.toString()))
            } else {
                emit(Resource.Success(geo.first!!))
            }
        } catch (e: Exception) {
            try {
                emit(Resource.Error(e.localizedMessage ?: "An expected error!"))
            } catch (_: Exception) { }
        }
    }
}