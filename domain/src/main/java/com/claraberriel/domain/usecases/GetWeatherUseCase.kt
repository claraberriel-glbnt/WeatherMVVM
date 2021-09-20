package com.claraberriel.domain.usecases

import com.claraberriel.domain.repositories.WeatherRepository

class GetWeatherUseCase {
    lateinit var weatherRepository: WeatherRepository
    operator fun invoke() = weatherRepository.getWeather()
}