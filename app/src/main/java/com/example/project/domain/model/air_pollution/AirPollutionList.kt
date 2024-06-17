package com.example.project.domain.model.air_pollution


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionList(
    @SerialName("components")
    val components: Components,
    @SerialName("dt")
    val dt: Long,
    @SerialName("main")
    val main: Main
)