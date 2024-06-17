package com.example.project.data.remote

import com.example.project.domain.model.air_pollution.AirPollution
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.domain.model.forecast.Forecast
import com.example.project.domain.model.search.Geo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/2.5/weather")
    fun currentWeather(
        @Query("lat") lat : String,
        @Query("lon") lon: String,
        @Query("units") units : String,
        @Query("appid") appid : String,
    ) : Call<CurrentWeather>

    @GET("data/2.5/forecast")
    fun forecast(
        @Query("lat") lat : String,
        @Query("lon") lon: String,
        @Query("units") units : String,
        @Query("appid") appid : String,
    ) : Call<Forecast>

    @GET("data/2.5/air_pollution")
    fun airPollution(
        @Query("lat") lat : String,
        @Query("lon") lon: String,
        @Query("appid") appid : String,
    ) : Call<AirPollution>

    @GET("geo/1.0/direct")
    fun search(
        @Query("q") q : String,
        @Query("limit") limit : Int,
        @Query("appid") appid : String,
    ) : Call<List<Geo>>

}