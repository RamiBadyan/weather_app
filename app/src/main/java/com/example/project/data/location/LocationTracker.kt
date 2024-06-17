package com.example.project.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}