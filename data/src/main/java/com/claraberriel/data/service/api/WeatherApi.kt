package com.claraberriel.data.service.api

import com.claraberriel.data.service.response.OneCallResponse
import com.claraberriel.data.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("part") part: String,
        @Query("units") units: String = Constants.UNITS,
        @Query("appid") apiKey: String
    ): Call<OneCallResponse>

}