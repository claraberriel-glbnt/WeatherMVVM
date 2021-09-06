package com.claraberriel.data.di

import com.claraberriel.data.service.api.WeatherApi
import com.claraberriel.data.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AppModule {

    fun provideBaseUrl() = Constants.BASE_URL

    fun provideRetrofitInstance(BASE_URL:String): WeatherApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
}