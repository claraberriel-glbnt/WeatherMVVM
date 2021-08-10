package com.claraberriel.weathermvvm.model

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Int,
    val temp_kf: Double,
    val temp_max: Int,
    val temp_min: Double
)