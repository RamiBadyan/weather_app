package com.example.project.domain.model.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geo(
    @SerialName("name")
    val name: String,
    @SerialName("lat")
    val lat: String,
    @SerialName("lon")
    val lon: String,
    @SerialName("country")
    val country: String,
    @SerialName("state")
    val state: String? = null
)
