package com.claraberriel.weathermvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.claraberriel.domain.entities.Weather
import com.claraberriel.weathermvvm.R
import com.claraberriel.weathermvvm.databinding.ActivityMainBinding
import com.claraberriel.weathermvvm.utils.Data
import com.claraberriel.weathermvvm.utils.Event
import com.claraberriel.weathermvvm.utils.Status
import com.claraberriel.weathermvvm.utils.WeatherAdapter
import com.claraberriel.weathermvvm.viewmodel.AppViewModelProvider
import com.claraberriel.weathermvvm.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        AppViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.oneCallResp.observe(::getLifecycle, ::updateUI)
        viewModel.getWeather()
    }

    private fun initRecycler(weatherList: List<Weather>) {
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = WeatherAdapter(weatherList)
        binding.recyclerview.adapter = adapter
    }

    private fun updateUI(weatherData: Event<Data<List<Weather>>>) {
        when (weatherData.peekContent().responseType) {
            Status.ERROR -> {
                hideProgress()
                weatherData.peekContent().error?.message?.let { showMessage(it) }
                binding.mainCityTitle.text = getString(R.string.no_weather)
            }
            Status.LOADING -> {
                showProgress()
            }
            Status.SUCCESSFUL -> {
                hideProgress()
                weatherData.peekContent().data?.let {
                    initRecycler(it)
                }
            }
        }
    }

    private fun showProgress() {
        binding.progressHorizontal.visibility = View.VISIBLE
        binding.mainCityTitle.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        binding.progressHorizontal.visibility = View.INVISIBLE
        binding.mainCityTitle.visibility = View.VISIBLE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
