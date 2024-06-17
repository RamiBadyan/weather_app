package com.example.project.domain.model.air_pollution


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Components(
    @SerialName("co")
    val co: Double,
    @SerialName("nh3")
    val nh3: Double,
    @SerialName("no")
    val no: Double,
    @SerialName("no2")
    val no2: Double,
    @SerialName("o3")
    val o3: Double,
    @SerialName("pm10")
    val pm10: Double,
    @SerialName("pm2_5")
    // There was an issue with the name of this parameter.
    // When named "pm25," it consistently received a value of 0.0 across different locations.
    // I'm not sure why the @SerialName annotation wasn't working as expected.
    // To resolve this, I changed the name from "pm25" to "pm2_5" to match the key in the API response.
    val pm2_5: Double,
    @SerialName("so2")
    val so2: Double
)