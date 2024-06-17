package com.example.project.presentation.home

import com.example.project.domain.model.search.Geo

data class SearchState(
    val geos: List<Geo> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false,
)
