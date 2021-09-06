package com.claraberriel.data.service

import android.content.Context
import android.util.Log
import com.claraberriel.data.WeatherRequestGenerator
import com.claraberriel.data.mapper.WeatherMapperLocal
import com.claraberriel.data.service.api.WeatherApi
import com.claraberriel.data.utils.Constants
import com.claraberriel.domain.entities.Weather
import com.claraberriel.domain.utils.Result
import java.io.IOException

class WeatherService(context: Context) {

    private val api: WeatherRequestGenerator = WeatherRequestGenerator(context)
    private val mapper: WeatherMapperLocal = WeatherMapperLocal()

    fun getWeatherList(): Result<List<Weather>> {
        val callResponse = api.createService(WeatherApi::class.java)
            .getWeather(Constants.LAT, Constants.LON, Constants.PART)
        try {
            val response = callResponse.execute()
            if (response.isSuccessful) {
                Log.d("msg", "success")

                response.body()?.let {
                    mapper.transform(it)
                }?.let {
                    return Result.Success(it)

                }
            }
            return Result.Failure(Exception(response.message()))
        } catch (e: RuntimeException) {
            return Result.Failure(Exception("Bad request/response"))
        } catch (e: IOException) {
            return Result.Failure(Exception("Bad request/response"))
        }
    }
}
