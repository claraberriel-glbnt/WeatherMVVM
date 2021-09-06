package com.claraberriel.weathermvvm.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claraberriel.domain.entities.Weather
import com.claraberriel.domain.usecases.GetWeatherUseCase
import com.claraberriel.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel
constructor(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _resp = MutableLiveData<List<Weather>>()
    val oneCallResp: LiveData<List<Weather>>
        get() = _resp

    /*
    init {
        getWeather()
    }

     */

    fun getWeather() = viewModelScope.launch {
        val result = withContext(Dispatchers.IO) {
            getWeatherUseCase()
        }

        when (result) {
            is Result.Success -> {
                _resp.postValue(result.data)
            }
            is Result.Failure -> {
                Log.e("Tag", "getWeather Error Response: ", result.exception)
            }
        }

    }
}