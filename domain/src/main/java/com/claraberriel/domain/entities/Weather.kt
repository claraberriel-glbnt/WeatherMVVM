package com.claraberriel.domain.entities

class Weather(
    val date: Long,
    val humidity: Double,
    val temp: Double,
    val max: Double,
    val min: Double,
    val description: String,
    val icon: String,
    val title: String
)