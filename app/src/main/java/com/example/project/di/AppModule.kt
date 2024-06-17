package com.example.project.di

import android.app.Application
import com.example.project.common.Constants.BASE_URL
import com.example.project.data.location.DefaultLocationTracker
import com.example.project.data.location.LocationTracker
import com.example.project.data.remote.APIService
import com.example.project.data.remote.WeatherDataSource
import com.example.project.data.repository.WeatherRepositoryImp
import com.example.project.domain.repository.WeatherRepository
import com.example.project.domain.use_cases.GetWeatherUseCase
import com.example.project.domain.use_cases.SearchUseCase
import com.example.project.domain.use_cases.UseCases
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)


    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource) : WeatherRepository {
        return WeatherRepositoryImp(weatherDataSource)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: WeatherRepository): UseCases {
        return UseCases(
            getWeatherUseCase = GetWeatherUseCase(repository),
            searchUseCase = SearchUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideWeatherDataSource(apiService: APIService): WeatherDataSource {
        return WeatherDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = DefaultLocationTracker(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )

}