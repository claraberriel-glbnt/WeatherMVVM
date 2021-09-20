package com.claraberriel.data.mapper

import com.claraberriel.data.service.response.OneCallResponse
import com.claraberriel.data.utils.Constants
import com.claraberriel.domain.entities.Weather

class WeatherMapperLocal : BaseMapperRepository<OneCallResponse, List<Weather>> {

    override fun transform(type: OneCallResponse): List<Weather> {
        return type.oneCallDailyResponse.map {
            val weather = it.oneCallWeather.first()
            Weather(
                date = it.date,
                humidity = it.humidity,
                temp = it.oneCallTempResponse.day,
                max = it.oneCallTempResponse.max,
                min = it.oneCallTempResponse.min,
                description = weather.description,
                icon = String.format(Constants.API_ICON_URL, weather.icon),
                title = weather.main,
            )
        }
    }
}