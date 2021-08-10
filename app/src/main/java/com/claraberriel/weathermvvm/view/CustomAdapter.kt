package com.claraberriel.weathermvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claraberriel.weathermvvm.databinding.RowBinding
import com.claraberriel.weathermvvm.model.WeatherItem

class CustomAdapter(private val weatherList: List<WeatherItem>): RecyclerView.Adapter<CustomAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): WeatherViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: WeatherViewHolder, position: Int) {
        val weatherItem = weatherList[position]
        viewHolder.binding.run {
            rowActionImage
            rowTitleDayTv.text = weatherItem.dt_txt
            rowDescriptionTv.text = weatherItem.main.toString()
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class WeatherViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root) {
    }
}