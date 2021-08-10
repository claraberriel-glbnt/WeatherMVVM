package com.claraberriel.weathermvvm.api

import com.claraberriel.weathermvvm.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/data/2.5/forecast?q=Montevideo&units=metric&&cnt=5&appid=91ea1e9ab8743df154666b95df83374b")
    suspend fun getWeather():Response<WeatherResponse>

}