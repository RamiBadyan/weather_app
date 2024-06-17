package com.example.project.domain.model.current_weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    @SerialName("feels_like")
    // There was an issue with the name of this parameter.
    // When named "feelsLike," it consistently received a value of 0.0 across different locations.
    // I'm not sure why the @SerialName annotation wasn't working as expected.
    // To resolve this, I changed the name from "feelsLike" to "feels_like" to match the key in the API response.
    val feels_like: Double,
    @SerialName("grnd_level")
    val grndLevel: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("temp")
    val temp: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("temp_min")
    val tempMin: Double
)