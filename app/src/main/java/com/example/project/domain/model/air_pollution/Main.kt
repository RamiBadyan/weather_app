package com.example.project.domain.model.air_pollution


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    @SerialName("aqi")
    val aqi: Int
)