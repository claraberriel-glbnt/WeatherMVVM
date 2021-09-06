package com.claraberriel.domain.repositories

import com.claraberriel.domain.entities.Weather
import com.claraberriel.domain.utils.Result

interface WeatherRepository {
        fun getWeather(): Result<List<Weather>>
}