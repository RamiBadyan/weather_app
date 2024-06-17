package com.example.project.data.remote

import android.util.Log
import com.example.project.common.ApiResponse
import com.example.project.common.Constants
import com.example.project.domain.model.air_pollution.AirPollution
import com.example.project.domain.model.current_weather.CurrentWeather
import com.example.project.domain.model.forecast.Forecast
import com.example.project.domain.model.search.Geo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherDataSource @Inject constructor(
    private val apiService: APIService
) {

    private val tag = "WeatherDataSource"

    private suspend fun currentWeather(lat: String, lon: String): Pair<CurrentWeather?, String?> {

        return suspendCoroutine { continuation ->

            val request = apiService.currentWeather(
                lat = lat,
                lon = lon,
                units = "metric",
                appid = Constants.API_KEY,
            )

            request.enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {
                        val currentWeather = response.body()
                        Log.d(tag, currentWeather.toString())
                        continuation.resume(currentWeather to null)
                    } else {
                        Log.d(tag, "An expected error: ${response.code()} - ${response.message()}")
                        continuation.resume(null to "An expected error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    Log.d(tag, "Connection failed: ${t.message}")
                    continuation.resume(null to "Connection failed: ${t.message}")
                }
            })
        }
    }

    private suspend fun forecast(lat: String, lon: String): Pair<Forecast?, String?> {

        return suspendCoroutine { continuation ->

            val request = apiService.forecast(
                lat = lat,
                lon = lon,
                units = "metric",
                appid = Constants.API_KEY,
            )

            request.enqueue(object : Callback<Forecast> {
                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    if (response.isSuccessful) {
                        val forecast = response.body()
                        Log.d(tag, forecast.toString())
                        continuation.resume(forecast to null)
                    } else {
                        Log.d(tag, "An expected error: ${response.code()} - ${response.message()}")
                        continuation.resume(null to "An expected error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Forecast>, t: Throwable) {
                    Log.d(tag, "Connection failed: ${t.message}")
                    continuation.resume(null to "Connection failed: ${t.message}")
                }
            })
        }
    }

    private suspend fun airPollution(lat: String, lon: String): Pair<AirPollution?, String?> {

        return suspendCoroutine { continuation ->

            val request = apiService.airPollution(
                lat = lat,
                lon = lon,
                appid = Constants.API_KEY,
            )

            request.enqueue(object : Callback<AirPollution> {
                override fun onResponse(
                    call: Call<AirPollution>,
                    response: Response<AirPollution>
                ) {
                    if (response.isSuccessful) {
                        val airPollution = response.body()
                        Log.d(tag, airPollution.toString())
                        continuation.resume(airPollution to null)
                    } else {
                        Log.d(tag, "An expected error: ${response.code()} - ${response.message()}")
                        continuation.resume(null to "An expected error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<AirPollution>, t: Throwable) {
                    Log.d(tag, "Connection failed: ${t.message}")
                    continuation.resume(null to "Connection failed: ${t.message}")
                }
            })
        }
    }

    suspend fun getWeather(lat: String, lon: String): ApiResponse {

        return coroutineScope {
            // Fetch data from both APIs concurrently
            val currentWeatherDeferred = async { currentWeather(lat, lon) }
            val forecastDeferred = async { forecast(lat, lon) }
            val airPollutionDeferred = async { airPollution(lat, lon) }

            // Await the results from both API calls
            val currentWeather = currentWeatherDeferred.await()
            val forecast = forecastDeferred.await()
            val airPollution = airPollutionDeferred.await()

            // Combine the results into a single response
            ApiResponse(
                currentWeather = currentWeather,
                forecast = forecast,
                airPollution = airPollution,
            )
        }
    }

    suspend fun search(q: String, limit: Int): Pair<List<Geo>?, String?> {

        return suspendCoroutine { continuation ->

            val request = apiService.search(
                q = q,
                limit = limit,
                appid = Constants.API_KEY,
            )

            request.enqueue(object : Callback<List<Geo>> {
                override fun onResponse(call: Call<List<Geo>>, response: Response<List<Geo>>) {
                    if (response.isSuccessful) {
                        val geo = response.body()
                        continuation.resume(geo to null)
                    } else {
                        Log.d(tag, "An expected error: ${response.code()} - ${response.message()}")
                        continuation.resume(null to "An expected error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<Geo>>, t: Throwable) {
                    Log.d(tag, "Connection failed: ${t.message}")
                    continuation.resume(null to "Connection failed: ${t.message}")
                }
            })
        }
    }

}