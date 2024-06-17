package com.example.project.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import com.example.project.R
import com.example.project.ui.theme.darkRedColor
import com.example.project.ui.theme.orangeColor
import com.example.project.ui.theme.redColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Functions {

    // Format date, example -> Wed, May 16
    fun formatDate(dateLong: Long): String {
        val date = Date(dateLong * 1000) // Convert (dateLong) from Seconds to Milliseconds
        val outputDateFormat = SimpleDateFormat("EEE, MMMM dd", Locale.ENGLISH)
        return outputDateFormat.format(date)
    }

    // Format date, example -> 10:55 PM
    fun formatTime(dateLong: Long): String {
        val date = Date(dateLong * 1000) // Convert (dateLong) from Seconds to Milliseconds
        val outputDateFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
        return outputDateFormat.format(date)
    }


    // Format date, example -> Friday 10 AM
    fun formatDateToWeekAndHour(dateLong: Long): String {
        val date = Date(dateLong * 1000) // Convert (dateLong) from Seconds to Milliseconds
        val outputDateFormat = SimpleDateFormat("EEEE h a", Locale.ENGLISH)
        return outputDateFormat.format(date)
    }

    // Check if internet available
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            }
        }
        return result
    }

    // Air Quality Index.
    // Possible values: 1, 2, 3, 4, 5. Where 1 = Good, 2 = Fair, 3 = Moderate, 4 = Poor, 5 = Very Poor.
    fun aQIToTextAndColor(aqi: Int): Pair<String, Color> {
        return when (aqi) {
            1 -> Pair("Good", Color.Green)
            2 -> Pair("Fair", Color.Yellow)
            3 -> Pair("Moderate", orangeColor)
            4 -> Pair("Poor", redColor)
            5 -> Pair("Very Poor", darkRedColor)
            else -> Pair("Unknown", Color.Gray)
        }
    }

    // Get icon of current weather
    fun getIcon(icon: String): Int {

        return when(icon) {
            "01d" -> {
                R.drawable.weather_icons_01d
            }
            "01n" -> {
                R.drawable.weather_icons_01n
            }
            "02d" -> {
                R.drawable.weather_icons_02d
            }
            "02n" -> {
                R.drawable.weather_icons_02n
            }
            "03d" -> {
                R.drawable.weather_icons_03d
            }
            "03n" -> {
                R.drawable.weather_icons_03n
            }
            "04d" -> {
                R.drawable.weather_icons_04d
            }
            "04n" -> {
                R.drawable.weather_icons_04n
            }
            "09d" -> {
                R.drawable.weather_icons_09d
            }
            "09n" -> {
                R.drawable.weather_icons_09n
            }
            "10d" -> {
                R.drawable.weather_icons_10d
            }
            "10n" -> {
                R.drawable.weather_icons_10n
            }
            "11d" -> {
                R.drawable.weather_icons_11d
            }
            "11n" -> {
                R.drawable.weather_icons_11n
            }
            "13d" -> {
                R.drawable.weather_icons_13d
            }
            "13n" -> {
                R.drawable.weather_icons_13n
            }
            "50d" -> {
                R.drawable.weather_icons_50d
            }
            "50n" -> {
                R.drawable.weather_icons_50n
            }
            else -> {
                R.drawable.weather_icons_03n
            }
        }
    }

}