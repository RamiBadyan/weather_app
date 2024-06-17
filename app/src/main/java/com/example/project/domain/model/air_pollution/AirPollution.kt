package com.example.project.domain.model.air_pollution


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollution(
    @SerialName("coord")
    val coord: Coord,
    @SerialName("list")
    val list: List<AirPollutionList>
)