package com.claraberriel.data.repository

import com.claraberriel.data.service.WeatherService
import com.claraberriel.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(private val weatherService: WeatherService) : WeatherRepository {

    override fun getWeather() = weatherService.getWeatherList()

}