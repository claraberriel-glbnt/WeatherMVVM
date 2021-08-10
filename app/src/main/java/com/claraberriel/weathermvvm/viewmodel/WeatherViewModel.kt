package com.claraberriel.weathermvvm.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claraberriel.weathermvvm.model.WeatherResponse
import com.claraberriel.weathermvvm.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _resp = MutableLiveData<WeatherResponse>()
    val weatherResp:LiveData<WeatherResponse>
    get() = _resp

    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        repository.getWeather().let { response ->

            if (response.isSuccessful){
                _resp.postValue(response.body())
            }else{
                Log.d("Tag", "getWeather Error Response: ${response.message()}")
            }

        }
    }
}