package com.claraberriel.data.mapper

import com.claraberriel.data.service.response.OneCallResponse
import com.claraberriel.domain.entities.Weather

class WeatherMapperLocal : BaseMapperRepository<OneCallResponse, List<Weather>> {

    override fun transform(type: OneCallResponse): List<Weather> {
        return type.oneCallDailyResponse.map {
            val weather = it.oneCallWeather.first()
            Weather(
                date = it.dt,
                humidity = it.humidity,
                temp = it.oneCallTempResponse.day,
                max = it.oneCallTempResponse.max,
                min = it.oneCallTempResponse.min,
                description = weather.description,
                icon = weather.icon,
                title = weather.main,
            )
        }
    }
}