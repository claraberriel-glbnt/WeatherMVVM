package com.claraberriel.weathermvvm.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.claraberriel.data.utils.Constants
import com.claraberriel.data.utils.Constants.CELSIUS_IDENTIFIER
import com.claraberriel.domain.entities.Weather
import com.claraberriel.weathermvvm.databinding.RowBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class WeatherAdapter(private val weatherList: List<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(private val binding: RowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun holderBindingRun(holder: WeatherViewHolder, weatherItem: Weather) {
            holder.binding.run {
                rowTemperature.text = convertToCelsius(weatherItem.temp)
                rowDescriptionTv.text =
                    SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault()).format(
                        Date(weatherItem.date * 1000)
                    )
                Glide.with(holder.itemView.context).load(weatherItem.icon).into(rowActionImage)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, number: Int): WeatherViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: WeatherViewHolder, position: Int) {
        val weatherItem = weatherList[position]
        viewHolder.holderBindingRun(viewHolder, weatherItem)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    private fun convertToCelsius(temp: Double): String {
        return String.format(Constants.DECIMAL_SPACES, temp, CELSIUS_IDENTIFIER)
    }
}