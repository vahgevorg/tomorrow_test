package com.tomorrow.weatherapp.feature.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tomorrow.weatherapp.R
import com.tomorrow.weatherapp.core.formatter.DateTimeFormatter.formatIso8601ToFullDateTime
import com.tomorrow.weatherapp.databinding.ItemViewWeatherForecastBinding
import com.tomorrow.weatherapp.domain.model.HourlyWeatherDomainModel
import com.tomorrow.weatherapp.domain.model.HourlyWeatherUnitsDomainModel

class WeatherForecastAdapter(
    private val hourlyWeatherUnitsDomainModel: HourlyWeatherUnitsDomainModel,
) : ListAdapter<HourlyWeatherDomainModel, RecyclerView.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewWeatherForecastBinding.inflate(inflater, parent, false)
        return WeatherForecastViewHolder(binding, hourlyWeatherUnitsDomainModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherForecastViewHolder).bind(getItem(position))
    }

    private inner class WeatherForecastViewHolder(
        private val binding: ItemViewWeatherForecastBinding,
        private val hourlyWeatherUnitsDomainModel: HourlyWeatherUnitsDomainModel,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HourlyWeatherDomainModel) {
            binding.apply {
                // time
                tvTime.apply {
                    text = formatIso8601ToFullDateTime(data.time)
                }
                // temperature
                val temperatureFormatted = String.format("%.1f", data.temperature2m)
                tvTemperature.apply {
                    text = context.getString(
                        R.string.weather_forecast_temperature_placeholder,
                        temperatureFormatted,
                        hourlyWeatherUnitsDomainModel.temperature2m
                    )
                }
            }
        }
    }
}

object DiffUtilCallback : DiffUtil.ItemCallback<HourlyWeatherDomainModel>() {
    override fun areItemsTheSame(
        oldItem: HourlyWeatherDomainModel,
        newItem: HourlyWeatherDomainModel
    ): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(
        oldItem: HourlyWeatherDomainModel,
        newItem: HourlyWeatherDomainModel
    ): Boolean {
        return oldItem == newItem
    }
}
