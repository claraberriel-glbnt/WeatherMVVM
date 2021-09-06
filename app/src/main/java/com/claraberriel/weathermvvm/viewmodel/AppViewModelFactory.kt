package com.claraberriel.weathermvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.claraberriel.data.repository.WeatherRepositoryImpl
import com.claraberriel.data.service.WeatherService
import com.claraberriel.domain.usecases.GetWeatherUseCase

class AppViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == WeatherViewModel::class.java) {
            WeatherViewModel(GetWeatherUseCase().apply {
                weatherRepository = WeatherRepositoryImpl(
                    WeatherService(context)
                )
            }) as T
        } else {
            super.create(modelClass)
        }
    }

}
