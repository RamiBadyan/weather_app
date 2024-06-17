# Weather App using Kotlin and Jetpack Compose

## Overview

My project is a weather app developed using Kotlin and Jetpack Compose. The app allows users to check the weather conditions in their current location or search for weather information in different cities worldwide. It utilizes the OpenWeather API to fetch weather data.

## Files and Structure

### Presentation Layer

- **HomeScreen**: This file (`HomeScreen.kt`) represents the main screen of the application. It contains the graphical user interface components and interacts with the `HomeViewModel` to display weather information.
- **HomeViewModel**: The `HomeViewModel.kt` file contains the ViewModel logic for the `HomeScreen`. It manages the application's state, fetches weather data, handles user interactions, and communicates with the domain layer.

### Domain Layer

- **Model**: This directory contains data models used throughout the application.
- **Repository**: The repository interface (`WeatherRepository.kt`) defines methods for fetching weather data.
- **Use Cases**: Use cases encapsulate the application's business logic.

### Data Layer

- **Location**: Contains the `DefaultLocationTracker.kt` file responsible for retrieving the user's current location.
- **Remote**: Contains the `APIService.kt` and `WeatherDataSource.kt` files. `APIService` handles API requests, while `WeatherDataSource` communicates with the OpenWeather API to fetch weather data.
- **Repository**: The repository interface (`WeatherRepository.kt`) defines methods inherited from `WeatherDataSource`.

### Dependency Injection

- **DI (Dependency Injection)**: This directory contains files related to dependency injection using Hilt.

### Other

- **Common**: Contains common utility functions and constants.
- **Core**: Contains shared files used across the application.

## Design Choices

- **MVVM Architecture**: The project follows the MVVM (Model-View-ViewModel) architecture to separate concerns and facilitate testability.
- **Modularization**: UI components are separated into different files within the `Components` directory to maintain code readability and organization.
- **Coroutines**: Asynchronous programming is handled using Kotlin Coroutines for efficient and non-blocking operations.
- **Dependency Injection**: Hilt is used for dependency injection to provide a cleaner and more maintainable codebase.

---

## Explanation of functions

**Here's an explanation of the functions and responsibilities present in the `HomeViewModel` class:**

1. **getCurrentLocation()**: This function is responsible for fetching the user's current location. It checks for internet availability, enables location services if necessary, retrieves the current location using the `LocationTracker`, and then calls the `getWeather()` function to fetch weather data based on the current location.

2. **getWeather()**: This function fetches weather data including current weather, forecast, and air pollution information using the `useCases` provided. It updates the state of the application accordingly, such as setting loading states, updating weather information on success, or displaying error messages on failure.

3. **searchForLocation(query: String)**: This function is used for searching weather information for a specific city based on the provided query. It checks for internet availability, initiates a search request using the `useCases`, and updates the search state accordingly with loading indicators, search results, or error messages.

4. **isLocationEnabled()**: This utility function checks whether location services are enabled on the device using the `LocationManager`.

5. **enableLocation()**: This function prompts the user to enable location services by opening the device's location settings.

6. **changeLocation(lat: String, lon: String)**: This function is invoked when the user selects a city from the search results. It updates the latitude and longitude values and fetches weather data for the newly selected location using the `getWeather()` function.

7. **dismissDialog()**: This function hides any informational dialogs displayed on the screen by updating the state of the application to dismiss the dialog.

These functions collectively manage the application's state, handle user interactions, fetch weather data, and ensure a seamless user experience within the Weather App.

**The `WeatherDataSource` class is responsible for interacting with the weather API service (`apiService`) to fetch various weather-related data. Here's an explanation of the files and functions within the class:**

1. **currentWeather(lat: String, lon: String)**:

   - This function sends a request to the weather API service to fetch the current weather data based on the provided latitude and longitude coordinates.
   - It uses suspend functions and `suspendCoroutine` to handle asynchronous execution.
   - The response is received asynchronously through an `enqueue` callback, where the retrieved data is logged and then resumed using Kotlin coroutines.

2. **forecast(lat: String, lon: String)**:

   - Similar to `currentWeather()`, this function fetches forecast weather data based on the provided latitude and longitude coordinates.
   - It also utilizes `suspendCoroutine` to handle asynchronous execution and receives the response asynchronously through an `enqueue` callback.

3. **airPollution(lat: String, lon: String)**:

   - This function fetches air pollution data based on the provided latitude and longitude coordinates.
   - Like the previous functions, it uses `suspendCoroutine` to handle asynchronous execution and receives the response asynchronously through an `enqueue` callback.

4. **getWeather(lat: String, lon: String)**:

   - This function orchestrates the fetching of weather-related data by concurrently calling `currentWeather`, `forecast`, and `airPollution`.
   - It uses Kotlin coroutines to execute these calls concurrently and awaits their results using `await`.
   - Once all data is fetched, it combines them into a single `ApiResponse` object and returns it.

5. **search(q: String, limit: Int)**:
   - This function sends a request to the weather API service to search for locations based on the provided query (`q`) and a limit on the number of results (`limit`).
   - It follows a similar pattern to the other functions, handling asynchronous execution with `suspendCoroutine` and processing the response asynchronously through an `enqueue` callback.

Overall, the `WeatherDataSource` class encapsulates the logic for interacting with the weather API service, handling asynchronous requests, and retrieving various weather-related data required by the application.

**The files in the Core folder provide a set of fundamental functions used throughout the application.
These functions range from formatting dates and times to checking internet availability, converting Air Quality Index to specific text and color, and retrieving the icon for the current weather.**

1. **formatDate(dateLong: Long)**:

   - This function formats the date provided as a parameter based on the time passed in seconds.
   - The expected result is a date like "Wed, May 16".

2. **formatTime(dateLong: Long)**:

   - This function formats the time provided as a parameter based on the time passed in seconds.
   - The expected result is a time like "10:55 PM".

3. **formatDateToWeekAndHour(dateLong: Long)**:

   - Similar to `formatTime`, this function formats the date to include the day of the week and the hour.
   - The expected result is a format like "Friday 10 AM".

4. **isInternetAvailable(context: Context)**:

   - This function checks whether the internet is available by accessing the device's connectivity services.
   - It returns a boolean indicating internet availability.

5. **aQIToTextAndColor(aqi: Int)**:

   - This function converts the Air Quality Index to a corresponding text and color.
   - It returns a pair containing the text and color associated with the provided AQI value.

6. **getIcon(icon: String)**:
   - This function retrieves the icon for the current weather based on the provided weather icon code.
   - It returns the resource ID of the corresponding weather icon drawable.

---

## Conclusion

The Weather App project aims to provide users with an intuitive and feature-rich experience for accessing weather information. The use of Kotlin, Jetpack Compose, and modern Android development practices ensures a robust and efficient application.
