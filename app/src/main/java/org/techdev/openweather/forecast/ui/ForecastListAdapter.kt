package org.techdev.openweather.forecast.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techdev.openweather.R
import org.techdev.openweather.extensions.*
import org.techdev.openweather.forecast.domain.model.Forecast


class ForecastListAdapter :
        ListAdapter<Forecast, ForecastListAdapter.ForecastViewHolder>(ForecastCallback()) {

    /**
     * PRO: Actualiza el set de pictures desde un observable
     */
    fun setSubmitList(forecasts: List<Forecast>) {
        submitList(forecasts)
        notifyDataSetChanged()
    }

    class ForecastViewHolder(forecastView: View) : RecyclerView.ViewHolder(forecastView) {

        private val date = forecastView.findViewById<TextView>(R.id.date)
        private val image = forecastView.findViewById<ImageView>(R.id.image)
        private val minTemp = forecastView.findViewById<TextView>(R.id.min_temp)
        private val maxTemp = forecastView.findViewById<TextView>(R.id.max_temp)


        @SuppressLint("SetTextI18n")
        fun bind(forecast: Forecast) {
            date.text = forecast.dt_txt
            image.loadFromUrl(
                getIconUrl(forecast.weather.icon)
            )
            minTemp.text = convertToDegreeCelcius(forecast.main.temp_min.toDouble()) + "°"
            maxTemp.text = convertToDegreeCelcius(forecast.main.temp_max.toDouble()) + "°"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_forecast, parent, false
            )
        )

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class ForecastCallback : DiffUtil.ItemCallback<Forecast>() {
    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem == newItem
    }

}