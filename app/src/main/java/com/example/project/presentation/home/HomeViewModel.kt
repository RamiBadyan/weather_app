package com.example.project.presentation.home

import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.R
import com.example.project.common.Resource
import com.example.project.core.Functions
import com.example.project.data.location.LocationTracker
import com.example.project.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases,
    private val locationTracker: LocationTracker,
    private val application: Application
) : ViewModel() {

    private val tag = "HomeViewModel"

    private var currentLocation by mutableStateOf<Location?>(null)
    var askForEnableLocation by mutableStateOf(false)
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state
    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState
    private var searchJob: Job? = null

    // Default value -> New York
    private var latitude = "40.6452148"
    private var longitude = "-73.8627688"

    fun getCurrentLocation() {

        askForEnableLocation = false

        if (!Functions.isInternetAvailable(application)) {
            _state.value = _state.value.copy(error = application.getString(R.string.Error_check_internet_connection), loading = false)
            Toast.makeText(application, application.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
            return
        }

        // Check if location services are enabled
        if (!isLocationEnabled()) {
            // Location services are not enabled, prompt the user to enable them
            _state.value = _state.value.copy(infoDialog = true)
            return
        }

        // Location services are enabled
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation() // Location
            if (currentLocation != null) {
                latitude = currentLocation!!.latitude.toString()
                longitude = currentLocation!!.longitude.toString()
            }
            getWeather()
        }
    }

    // Get current Weather, Forecast And Air Pollution
    private fun getWeather() {

        askForEnableLocation = false
        _state.value = _state.value.copy(error = null, currentWeather = null, forecast = null)
        useCases.getWeatherUseCase(lat = latitude, lon = longitude).onEach { result ->

            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        currentWeather = result.data!!.currentWeather.first,
                        forecast = result.data.forecast.first,
                        airPollution = result.data.airPollution.first,
                        loading = false
                    )
                }

                is Resource.Error -> {
                    Log.d(tag, "Error: ${result.message}")
                    _state.value = _state.value.copy(error = result.message, loading = false)
                }

                is Resource.Loading -> {
                    Log.d(tag, "Loading")
                    _state.value = _state.value.copy(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    // Search For A City
    fun searchForLocation(query: String) {

        if (!Functions.isInternetAvailable(application)) {
            _searchState.value = _searchState.value.copy(error = application.getString(R.string.check_internet_connection), loading = false)
            return
        }

        if (query.isNotBlank()) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(800)
                _searchState.value = _searchState.value.copy(error = null, geos = emptyList(), loading = false)
                useCases.searchUseCase(q = query, limit = 5).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data!!.isEmpty()) {
                                _searchState.value = _searchState.value.copy(
                                    geos = emptyList(),
                                    error = application.getString(R.string.could_not_find_anything),
                                    loading = false
                                )
                            } else {
                                _searchState.value = _searchState.value.copy(
                                    geos = result.data,
                                    error = null,
                                    loading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            Log.d(tag, "Error: ${result.message}")
                            _searchState.value = _searchState.value.copy(error = application.getString(R.string.could_not_find_anything), loading = false)
                        }

                        is Resource.Loading -> {
                            Log.d(tag, "Loading")
                            _searchState.value = _searchState.value.copy(loading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    // Check if location services are enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // Prompt the user to enable location services
    fun enableLocation() {
        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        application.startActivity(settingsIntent)
    }

    // This Function will be execute when the user click on a city from Search screen
    fun changeLocation(lat: String, lon: String) {
        latitude = lat
        longitude = lon
        getWeather()
    }

    // Hide dialog
    fun dismissDialog() {
        _state.value = _state.value.copy(infoDialog = false)
    }

}