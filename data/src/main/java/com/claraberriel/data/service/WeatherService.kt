package com.claraberriel.data.service

import android.content.Context
import android.util.Log
import com.claraberriel.data.mapper.WeatherMapperLocal
import com.claraberriel.data.service.api.WeatherApi
import com.claraberriel.data.utils.Constants
import com.claraberriel.domain.entities.Weather
import com.claraberriel.domain.utils.Result
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class WeatherService(context: Context) {

    companion object {

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val CONNECT_TIMEOUT: Long = 1
        private const val READ_TIMEOUT: Long = 30
        private const val WRITE_TIMEOUT: Long = 15

    }

    private val mapper: WeatherMapperLocal = WeatherMapperLocal()

    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    fun getWeatherList(): Result<List<Weather>> {
        val callResponse = getRetrofit().create(WeatherApi::class.java)
            .getWeather(
                Constants.LAT,
                Constants.LON,
                Constants.PART,
                Constants.UNITS,
                Constants.KEY
            )
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
            Log.e("weather service", ".", e)
            return Result.Failure(Exception("Bad request/response"))
        } catch (e: IOException) {
            return Result.Failure(Exception("Bad request/response"))
        }
    }
}
