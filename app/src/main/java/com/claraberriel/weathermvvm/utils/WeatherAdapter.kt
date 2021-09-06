package com.claraberriel.weathermvvm.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claraberriel.domain.entities.Weather
import com.claraberriel.weathermvvm.databinding.RowBinding

class WeatherAdapter(private val weatherList: List<Weather>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(private val binding: RowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun holderBindingRun(holder: WeatherViewHolder, weatherItem: Weather) {
            holder.binding.run {
                rowTitleDayTv.text = weatherItem.date.toString()
                rowDescriptionTv.text = weatherItem.temp.toString()
                //getIcon(holder, weatherItem.image, imgWeather)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): WeatherViewHolder {
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
}