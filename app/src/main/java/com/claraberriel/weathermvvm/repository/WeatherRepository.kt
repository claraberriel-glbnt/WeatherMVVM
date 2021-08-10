package com.claraberriel.weathermvvm.repository

import com.claraberriel.weathermvvm.api.ApiService
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val apiService: ApiService){

    suspend fun getWeather() = apiService.getWeather()

}