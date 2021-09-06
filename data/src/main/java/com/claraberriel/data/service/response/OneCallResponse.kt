package com.claraberriel.data.service.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class OneCallResponse(
    @Json(name = "daily") val oneCallDailyResponse: List<OneCallDailyResponse>
): Serializable

@JsonClass(generateAdapter = true)
data class OneCallDailyResponse(
    @Json(name = "date")        val dt: Long,
    @Json(name = "humidity")    val humidity: Double,
    @Json(name = "temp")        val oneCallTempResponse: OneCallTempResponse,
    @Json(name = "weather")     val oneCallWeather: List<OneCallWeatherResponse>
): Serializable

@JsonClass(generateAdapter = true)
data class OneCallWeatherResponse(
    @Json(name = "description") val description: String,
    @Json(name = "icon")        val icon: String,
    @Json(name = "main")        val main: String
): Serializable

@JsonClass(generateAdapter = true)
data class OneCallTempResponse(
    @Json(name = "day") val day: Double,
    @Json(name = "max") val max: Double,
    @Json(name = "min") val min: Double,
): Serializable