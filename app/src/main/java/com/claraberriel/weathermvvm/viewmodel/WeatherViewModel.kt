package com.claraberriel.weathermvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claraberriel.domain.entities.Weather
import com.claraberriel.domain.usecases.GetWeatherUseCase
import com.claraberriel.domain.utils.Result
import com.claraberriel.weathermvvm.utils.Data
import com.claraberriel.weathermvvm.utils.Event
import com.claraberriel.weathermvvm.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel
constructor(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _response = MutableLiveData<Event<Data<List<Weather>>>>()
    val oneCallResp: LiveData<Event<Data<List<Weather>>>>
        get() = _response

    init {
        getWeather()
    }

    fun getWeather() = viewModelScope.launch {
        _response.value = Event(Data(responseType = Status.LOADING))
        val result = withContext(Dispatchers.IO) {
            getWeatherUseCase()
        }

        when (result) {
            is Result.Success -> {
                _response.postValue(
                    Event(
                        Data(
                            responseType = Status.SUCCESSFUL,
                            data = result.data
                        )
                    )
                )
            }
            is Result.Failure -> {
                _response.value = Event(Data(responseType = Status.ERROR, error = result.exception))
            }
        }

    }
}